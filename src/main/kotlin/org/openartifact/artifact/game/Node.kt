package org.openartifact.artifact.game

open class Node {

    var parent : Node? = null

    @Suppress("unused")
    val type : String = javaClass.simpleName

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

    open fun render(deltaTime : Double) {
        components.forEach {
            it.render(deltaTime)
        }
    }

    open fun update(physicsDeltaTime : Double) {
        components.forEach {
            it.update(physicsDeltaTime)
        }
    }

    open fun rest() {
        components.forEach {
            it.rest()
        }
    }

    open fun requiredComponents() : MutableList<Component> = mutableListOf()

}