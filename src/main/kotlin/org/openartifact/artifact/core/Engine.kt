package org.openartifact.artifact.core

import org.openartifact.artifact.core.graphics.GraphicsThread
import org.openartifact.artifact.game.Component
import org.openartifact.artifact.game.Node
import org.openartifact.artifact.game.components.TransformComponent
import org.openartifact.artifact.game.nodes.CubeNode
import org.openartifact.artifact.game.scene.SceneManager
import org.openartifact.artifact.utils.*
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

internal class Engine {

    private val logger = LoggerFactory.getLogger(javaClass)

    private lateinit var graphicsThread : GraphicsThread

    var engineState = EngineState.Running

    var componentClasses : MutableSet<KClass<out Component>> = mutableSetOf(
        TransformComponent::class
    )

    val nodeClasses : MutableList<KClass<out Node>> = mutableListOf(
        CubeNode::class
    )

    fun loadFileStructure() {
        createDirectory(getEngineDir())
        createDirectory(getProjectsDir())
        createDirectory(getDefaultProjectDir())
        createDirectory(getGameDataDir())
    }

    fun loadGraphics() {
        logger.debug("Detected rendering API: ${GameContext.current().engineProfile().renderAPI}")

        logger.debug("Launching Graphics Thread")
        graphicsThread = GraphicsThread()
        graphicsThread.start()
    }

}