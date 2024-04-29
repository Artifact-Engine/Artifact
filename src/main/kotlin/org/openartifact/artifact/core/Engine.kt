package org.openartifact.artifact.core

import org.openartifact.artifact.core.graphics.Graphics
import org.openartifact.artifact.core.graphics.component.CubeRenderer
import org.openartifact.artifact.core.graphics.window.Window
import org.openartifact.artifact.game.Component
import org.openartifact.artifact.game.Node
import org.openartifact.artifact.game.components.TransformComponent
import org.openartifact.artifact.game.nodes.CameraNode
import org.openartifact.artifact.game.nodes.CubeNode
import org.openartifact.artifact.utils.*
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

internal class Engine {

    private val logger = LoggerFactory.getLogger(javaClass)

    private lateinit var graphics : Graphics
    lateinit var window : Window

    var engineState = EngineState.Stopped

    var componentClasses : MutableSet<KClass<out Component>> = mutableSetOf(
        TransformComponent::class,
        CubeRenderer::class
    )

    val nodeClasses : MutableList<KClass<out Node>> = mutableListOf(
        CubeNode::class,
        CameraNode::class
    )

    private fun loadFileStructure() {
        createDirectory(getEngineDir())
        createDirectory(getGamesDir())
        createDirectory(getGameDir())
        createDirectory(getGameDataDir())
        createDirectory(getScenesDir())
        createDirectory(getShadersDir())
    }

    private fun loadGraphics() {
        logger.debug("Launching Graphics")
        graphics = Graphics().also { graphics ->
            graphics.run()
        }
    }

    internal fun run() {
        if (engineState == EngineState.Running) {
            logger.warn("Engine is already running. Ignoring run request.")
            return
        }

        engineState = EngineState.Running
        loadFileStructure()
        loadGraphics()
    }


}