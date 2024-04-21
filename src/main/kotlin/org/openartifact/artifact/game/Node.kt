package org.openartifact.artifact.game

open class Node {

    val type = javaClass.simpleName

    open val components : MutableList<Component> = mutableListOf()

    fun <T> getComponent() {

    }

    fun awake() {
        components.forEach {
            it.awake()
        }
    }

    fun update() {
        components.forEach {
            it.update()
        }
    }

    fun rest() {
        components.forEach {
            it.rest()
        }
    }

}