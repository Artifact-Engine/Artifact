package org.openartifact.artifact.core

import org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose
import org.lwjgl.opengl.GL30.*
import org.openartifact.artifact.core.graphics.window.PerformanceMonitor
import org.openartifact.artifact.core.graphics.window.WindowProfile
import org.openartifact.artifact.game.Component
import org.openartifact.artifact.game.Node
import org.openartifact.artifact.game.scene.SceneManager
import org.openartifact.artifact.utils.FileConstants
import org.openartifact.artifact.utils.applyDepthTest
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

class GameContext private constructor(
    private val applicationProfile : ApplicationProfile,
    private val windowProfile : WindowProfile
) {

    private val logger = LoggerFactory.getLogger(javaClass)
    lateinit var sceneManager : SceneManager
    internal val engine = Engine()

    lateinit var performanceMonitor : PerformanceMonitor

    /**
     * Starts the engine
     */
    fun run() : GameContext {
        sceneManager = SceneManager()
        sceneManager.loadScenesFromFile(FileConstants.scenes())
        engine.run()
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

    fun set() : GameContext {
        require(current != this) { "Context was already set to current." }
        current = this
        return this
    }

    fun requestShutdown() {
        sceneManager.activeScene?.rest()

        glfwSetWindowShouldClose(GameContext.current().windowProfile().windowId, true)
    }

    fun applicationProfile() : ApplicationProfile =
        applicationProfile ?: throw IllegalStateException("No application found. Was the engine initialized?")

    fun windowProfile() : WindowProfile =
        windowProfile

    companion object {

        private var current : GameContext? = null

        fun createContext(block : Builder.() -> Unit) : GameContext {
            val builder = Builder()
            block(builder)
            requireNotNull(builder.applicationProfile) { "No application profile was found! Create one in the context using configureApplicationProfile." }
            requireNotNull(builder.windowProfile) { "No window profile was found! Create one in the context using configureWindowProfile." }
            val gameContext = GameContext(builder.applicationProfile !!, builder.windowProfile !!)
            gameContext.logger.info("Context created")
            return gameContext
        }

        fun current() : GameContext =
            current ?: throw IllegalStateException("No current GameContext exists. Try using setCurrent.")
    }

    class Builder {
        var applicationProfile : ApplicationProfile? = null
        var windowProfile : WindowProfile? = null

        fun configureApplicationProfile(profile : ApplicationProfile) {
            applicationProfile = profile
        }

        fun configureWindowProfile(profile : WindowProfile) {
            windowProfile = profile
        }
    }

    internal fun load() {
        sceneManager.switchScene(applicationProfile.startingSceneId)
    }

    internal fun render(deltaTime : Double) {
        applyDepthTest()
        sceneManager.activeScene?.render(deltaTime)
    }

    internal fun update(physicsDeltaTime : Double) {
        sceneManager.activeScene?.update(physicsDeltaTime)
    }

    internal fun rest() {
        sceneManager.activeScene?.rest()
    }

}