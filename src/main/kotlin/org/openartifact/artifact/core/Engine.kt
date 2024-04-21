package org.openartifact.artifact.core

import org.openartifact.artifact.core.event.events.ComponentRegisterEvent
import org.openartifact.artifact.core.event.notify
import org.openartifact.artifact.core.graphics.GraphicsThread
import org.openartifact.artifact.game.Component
import org.openartifact.artifact.game.Node
import org.openartifact.artifact.game.components.TransformComponent
import org.openartifact.artifact.game.nodes.CubeNode
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

object Engine {

    private val logger = LoggerFactory.getLogger(javaClass)

    lateinit var application : Application
    lateinit var graphicsThread : GraphicsThread

    var componentClasses : MutableSet<KClass<out Component>> = mutableSetOf(
        TransformComponent::class
    )

    val nodeClasses : MutableList<KClass<out Node>> = mutableListOf(
        CubeNode::class
    )

    fun initApplication() {
        logger.debug("Reading application...")

        notify(ComponentRegisterEvent())

        application = Application()
    }

    fun initGraphics() {
        logger.debug("Detected rendering API: ${application.settings.rendererType}")

        logger.debug("Launching Graphics Thread")
        graphicsThread = GraphicsThread()
        graphicsThread.start()
    }

}