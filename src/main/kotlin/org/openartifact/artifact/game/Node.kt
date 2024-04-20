package org.openartifact.artifact.game

open class Node {

    val type = javaClass.simpleName

    open val components: MutableList<Component> = mutableListOf()

    fun awake() {}
    fun update() {}
    fun rest() {}

}