package org.openartifact.artifact.core

import org.openartifact.artifact.core.graphics.Graphics
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

    var engineState = EngineState.Running

    var componentClasses : MutableSet<KClass<out Component>> = mutableSetOf(
        TransformComponent::class
    )

    val nodeClasses : MutableList<KClass<out Node>> = mutableListOf(
        CubeNode::class,
        CameraNode::class
    )

    fun loadFileStructure() {
        createDirectory(getEngineDir())
        createDirectory(getGamesDir())
        createDirectory(getGameDir())
        createDirectory(getGameDataDir())
        createDirectory(getScenesDir())
        createDirectory(getShadersDir())
    }

    fun loadGraphics() {
        logger.debug("Launching Graphics Thread")
        graphics = Graphics().also { graphics ->
            graphics.run()
        }
    }

}