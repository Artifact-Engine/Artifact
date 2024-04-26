package org.openartifact.artifact.core

import org.openartifact.artifact.core.event.events.ComponentRegisterEvent
import org.openartifact.artifact.core.event.events.NodeRegisterEvent
import org.openartifact.artifact.core.event.notify
import org.openartifact.artifact.core.graphics.GraphicsThread
import org.openartifact.artifact.game.Component
import org.openartifact.artifact.game.Node
import org.openartifact.artifact.game.components.TransformComponent
import org.openartifact.artifact.game.nodes.CubeNode
import org.openartifact.artifact.game.scene.SceneManager
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

internal object Engine {

    private val logger = LoggerFactory.getLogger(javaClass)

    private lateinit var graphicsThread : GraphicsThread

    var componentClasses : MutableSet<KClass<out Component>> = mutableSetOf(
        TransformComponent::class
    )

    val nodeClasses : MutableList<KClass<out Node>> = mutableListOf(
        CubeNode::class
    )

    fun loadApplication(profile : ApplicationProfile) : Application {
        logger.debug("Loading application...")

        notify(NodeRegisterEvent())
        notify(ComponentRegisterEvent())

        return Application(profile, SceneManager())
    }

    fun loadGraphics() {
        logger.debug("Detected rendering API: ${GameContext.getCurrentContext().application.profile.renderAPI}")

        logger.debug("Launching Graphics Thread")
        graphicsThread = GraphicsThread()
        graphicsThread.start()
    }

}