package org.openartifact.core.application

import org.openartifact.node.Node
import org.openartifact.node.Scene
import org.openartifact.node.fixed.cube.CubeNode
import org.openartifact.node.fixed.cube.CubeRenderer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Holds information and handles Applications
 */
open class Application {

    private var logger: Logger = LoggerFactory.getLogger(javaClass)

    var currentScene: Scene? = null

    fun loadScene(scene: Scene) {
        logger.info("Loading Scene \"${scene.identifier}\" with ${scene.nodes.size} nodes")

        // Create a new list to hold the updated nodes
        val updatedNodes = mutableListOf<Node>()

        scene.nodes.forEach { node ->
            logger.info("Node class: ${node::class.java.name}, identifier: ${node.identifier}")
            if (node.identifier == "cube") {
                val cubeNodeX = CubeNode((node as CubeNode).worldCoordinate)
                updatedNodes.add(cubeNodeX)
            } else {
                updatedNodes.add(node)
            }
        }


        // Replace the original nodes list with the updated list
        scene.nodes.clear()
        scene.nodes.addAll(updatedNodes)

        currentScene = scene
    }


}