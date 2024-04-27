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

    /**
     * Performs necessary operations to load the scene
     */
    fun load() {
        nodes.forEach { node ->
            node.awake()
        }
    }

    /**
     * Performs necessary operations to unload the scene
     */
    fun rest() {
        nodes.forEach { node ->
            node.rest()
        }
    }

    /**
     * Performs necessary operations to update the scene
     */
    fun update() {
        nodes.forEach { node ->
            node.update()
        }
    }

}