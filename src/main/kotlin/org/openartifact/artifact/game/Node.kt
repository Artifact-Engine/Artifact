package org.openartifact.artifact.game

open class Node() {

    var parent : Node? = null

    val type = javaClass.simpleName

    val children : MutableList<Node> = mutableListOf()
    val components : MutableList<Component> = mutableListOf()

    open fun preAwake() {
        components.addAll(requiredComponents())

        components.forEach { component ->
            component.parent = this
        }
    }

    open fun awake() {
        components.forEach {
            it.awake()
        }
    }

    open fun update() {
        components.forEach {
            it.update()
        }
    }

    open fun rest() {
        components.forEach {
            it.rest()
        }
    }

    open fun requiredComponents() : MutableList<Component> = mutableListOf()

}