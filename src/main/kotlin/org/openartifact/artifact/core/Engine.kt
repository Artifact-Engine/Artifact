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
import org.openartifact.artifact.utils.FileConstants.engine
import org.openartifact.artifact.utils.FileConstants.games
import org.openartifact.artifact.utils.FileConstants.scenes
import org.openartifact.artifact.utils.FileConstants.shaders
import org.openartifact.artifact.utils.FileConstants.game
import org.openartifact.artifact.utils.FileConstants.gameData
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

internal class Engine {

    private val logger = LoggerFactory.getLogger(javaClass)

    private lateinit var graphics: Graphics
    lateinit var window: Window

    var engineState = EngineState.Stopped

    var componentClasses: MutableSet<KClass<out Component>> = mutableSetOf(
        TransformComponent::class,
        CubeRenderer::class
    )

    val nodeClasses: MutableList<KClass<out Node>> = mutableListOf(
        CubeNode::class,
        CameraNode::class
    )

    private fun loadFileStructure() {
        createDirectory(engine())
        createDirectory(games())
        createDirectory(game())
        createDirectory(gameData())
        createDirectory(scenes())
        createDirectory(shaders())
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