package org.openartifact.artifact.core

import org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose
import org.openartifact.artifact.core.graphics.window.WindowProfile
import org.openartifact.artifact.game.Component
import org.openartifact.artifact.game.Node
import org.openartifact.artifact.game.scene.SceneManager
import org.openartifact.artifact.utils.FileConstants
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

class Context private constructor(
    private val applicationProfile : ApplicationProfile,
    private val windowProfile : WindowProfile
) {

    private val logger = LoggerFactory.getLogger(javaClass)
    lateinit var sceneManager : SceneManager
    internal val engine = Engine()

    /**
     * Starts the engine
     */
    fun run() : Context {
        sceneManager = SceneManager()
        sceneManager.loadScenesFromFile(FileConstants.scenes())
        engine.run()
        return this
    }

    fun registerComponent(kClass : KClass<out Component>) : Context {
        require(! engine.componentClasses.contains(kClass)) { "Component ${kClass.simpleName} was already registered!" }
        engine.componentClasses.add(kClass)
        logger.debug("Registering component ${kClass.simpleName}")
        return this
    }

    fun registerNode(kClass : KClass<out Node>) : Context {
        require(! engine.nodeClasses.contains(kClass)) { "Node ${kClass.simpleName} was already registered!" }
        engine.nodeClasses.add(kClass)
        logger.debug("Registering node ${kClass.simpleName}")
        return this
    }

    fun set() : Context {
        require(current != this) { "Context was already set to current." }
        current = this
        return this
    }

    fun requestShutdown() {
        sceneManager.activeScene?.rest()

        glfwSetWindowShouldClose(Context.current().windowProfile().windowId, true)
    }

    fun applicationProfile() : ApplicationProfile =
        applicationProfile ?: throw IllegalStateException("No application found. Was the engine initialized?")

    fun windowProfile() : WindowProfile =
        windowProfile

    companion object {

        private var current : Context? = null

        fun createContext(block : Builder.() -> Unit) : Context {
            val builder = Builder()
            block(builder)
            requireNotNull(builder.applicationProfile) { "No application profile was found! Create one in the context using configureApplicationProfile." }
            requireNotNull(builder.windowProfile) { "No window profile was found! Create one in the context using configureWindowProfile." }
            val context = Context(builder.applicationProfile !!, builder.windowProfile !!)
            context.logger.info("Context created")
            return context
        }

        fun current() : Context =
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
        sceneManager.activeScene?.render(deltaTime)
    }

    internal fun update(physicsDeltaTime : Double) {
        sceneManager.activeScene?.update(physicsDeltaTime)
    }

    internal fun rest() {
        sceneManager.activeScene?.rest()
    }

}