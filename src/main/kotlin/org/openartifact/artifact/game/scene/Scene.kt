package org.openartifact.artifact.game.scene

import org.openartifact.artifact.game.Node

/**
 * Holds a collection of nodes
 */
class Scene(val profile : SceneProfile) {

    constructor(profile : SceneProfile, nodes : List<Node>) : this(profile) {
        this.nodes = nodes.toMutableList()
    }

    var nodes : MutableList<Node> = mutableListOf()

    private fun recursiveOperation(node: Node, operation: (Node) -> Unit) {
        operation(node)
        node.children.forEach { child ->
            recursiveOperation(child, operation)
        }
    }

    /**
     * Performs necessary operations to load the scene
     */
    fun load() {
        nodes.forEach { node ->
            recursiveOperation(node, Node::awake)
        }
    }

    /**
     * Performs necessary operations to unload the scene
     */
    fun update() {
        nodes.forEach { node ->
            recursiveOperation(node, Node::update)
        }
    }

    /**
     * Performs necessary operations to unload the scene
     */
    fun rest() {
        nodes.forEach { node ->
            recursiveOperation(node, Node::rest)
        }
    }


}