package org.openartifact.artifact.core

import org.openartifact.artifact.core.graphics.EngineProfile
import org.openartifact.artifact.core.graphics.window.WindowProfile
import org.openartifact.artifact.game.Component
import org.openartifact.artifact.game.Node
import org.openartifact.artifact.game.scene.SceneManager
import org.openartifact.artifact.utils.getScenesDir
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

class GameContext private constructor(private val applicationProfile : ApplicationProfile, private val windowProfile : WindowProfile, private val engineProfile : EngineProfile) {

    private val logger = LoggerFactory.getLogger(javaClass)
    private lateinit var sceneManager : SceneManager
    internal val engine = Engine()

    /**
     * Starts the engine
     */
    fun run() : GameContext {
        sceneManager = SceneManager()
        sceneManager.loadScenesFromFile(getScenesDir())
        sceneManager.switchScene(applicationProfile.startingSceneId)
        engine.loadFileStructure()
        engine.loadGraphics()
        return this
    }

    fun registerComponent(kClass : KClass<out Component>) : GameContext {
        require(! engine.componentClasses.contains(kClass)) { "Component ${kClass.simpleName} was already registered!" }
        engine.componentClasses.add(kClass)
        logger.debug("Registering component ${kClass.simpleName}")
        return this
    }

    fun registerNode(kClass : KClass<out Node>) : GameContext {
        require(! engine.nodeClasses.contains(kClass)) { "Node ${kClass.simpleName} was already registered!" }
        engine.nodeClasses.add(kClass)
        logger.debug("Registering node ${kClass.simpleName}")
        return this
    }

    fun setCurrent() : GameContext {
        require(current != this) { "GameContext was already set to current." }
        current = this
        return this
    }

    fun applicationProfile() : ApplicationProfile =
        applicationProfile ?: throw IllegalStateException("No application found. Was the engine initialized?")

    fun windowProfile() : WindowProfile =
        windowProfile

    fun engineProfile() : EngineProfile =
        engineProfile

    companion object {

        private var current : GameContext? = null

        fun createContext(block : Builder.() -> Unit) : GameContext {
            val builder = Builder()
            block(builder)
            val context = GameContext(builder.applicationProfile !!, builder.windowProfile !!, builder.engineProfile !!)
            context.logger.info("GameContext created")
            return context
        }

        fun current() : GameContext =
            current ?: throw IllegalStateException("No current GameContext exists. Try using setCurrent.")
    }

    class Builder {
        var applicationProfile : ApplicationProfile? = null
        var windowProfile : WindowProfile? = null
        var engineProfile : EngineProfile? = null

        fun configureApplicationProfile(profile : ApplicationProfile) {
            applicationProfile = profile
        }

        fun configureWindowProfile(profile : WindowProfile) {
            windowProfile = profile
        }

        fun configureEngineProfile(profile : EngineProfile) {
            engineProfile = profile
        }
    }

    fun update() {
        sceneManager.activeScene?.update()
    }

    fun rest() {
        sceneManager.activeScene?.rest()
    }

}