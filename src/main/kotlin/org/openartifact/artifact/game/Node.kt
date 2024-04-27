package org.openartifact.artifact.game

import org.openartifact.artifact.core.graphics.node.NodeRenderer

open class Node() {

    var parent : Node? = null

    constructor(renderer : NodeRenderer<out Node>) : this() {
        this.renderer = renderer
    }

    var renderer : NodeRenderer<out Node>? = null

    val type = javaClass.simpleName

    open val children : MutableList<Node> = mutableListOf()
    open val components : MutableList<Component> = mutableListOf()

    open fun awake() {
        components.forEach {
            it.awake()
        }

        renderer?.initRender()
    }

    open fun update() {
        components.forEach {
            it.update()
        }

        renderer?.render()
    }

    open fun rest() {
        components.forEach {
            it.rest()
        }

        renderer?.cleanupRender()
    }

}