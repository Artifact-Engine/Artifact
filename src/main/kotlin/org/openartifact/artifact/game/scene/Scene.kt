package org.openartifact.artifact.game.scene

import org.openartifact.artifact.game.Node


/**
 * Holds a collection of nodes
 */
class Scene(val sceneProfile : SceneProfile) {

    constructor(sceneProfile : SceneProfile, nodes : List<Node>) : this(sceneProfile) {
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