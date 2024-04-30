package org.openartifact.artifact.game.scene

import org.openartifact.artifact.game.Node
import org.openartifact.artifact.game.nodes.CameraNode

/**
 * Holds a collection of nodes
 */
class Scene(val profile : SceneProfile) {

    constructor(profile : SceneProfile, nodes : List<Node>) : this(profile) {
        this.nodes = nodes.toMutableList()
    }

    var nodes : MutableList<Node> = mutableListOf()

    lateinit var camera : CameraNode

    private fun recursiveOperation(node: Node, operation: (Node) -> Unit) {
        operation(node)
        node.children.forEach { child ->
            recursiveOperation(child, operation)
        }
    }

    /**
     * Performs necessary operations to load the scene
     */
    internal fun load() {
        require(nodes.find { it is CameraNode } != null) { "The scene needs a camera node to render." }

        camera = nodes.find { it is CameraNode } as CameraNode

        nodes.forEach { node ->
            recursiveOperation(node, Node::preAwake)
        }

        nodes.forEach { node ->
            recursiveOperation(node, Node::awake)
        }
    }

    /**
     * Performs necessary operations to unload the scene
     */
    internal fun update(deltaTime : Double) {
        nodes.forEach { node ->
            recursiveOperation(node) { rNode ->
                rNode.update(deltaTime)
            }
        }
    }

    /**
     * Performs necessary operations to unload the scene
     */
    internal fun rest() {
        nodes.forEach { node ->
            recursiveOperation(node, Node::rest)
        }
    }


}