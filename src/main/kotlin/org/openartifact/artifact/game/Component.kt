package org.openartifact.artifact.game

open class Component {

    lateinit var parent : Node

    inline fun <reified T : Component> getComponent(): T? =
        parent.components.find { it is T } as? T

    @Suppress("unused")
    val type : String = javaClass.simpleName

    open fun awake() {}
    open fun update(deltaTime : Double) {}
    open fun rest() {}

}