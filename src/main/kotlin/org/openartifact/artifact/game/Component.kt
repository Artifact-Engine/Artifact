package org.openartifact.artifact.game

open class Component {

    lateinit var parent : Node

    inline fun <reified T : Component> getComponent(): T? =
        parent.components.find { it is T } as? T

    @Suppress("unused")
    val type : String = javaClass.simpleName

    open fun awake() {}
    open fun render(deltaTime : Double) {}

    /**
     * Physics update
     */
    open fun update(physicsDeltaTime : Double) {}
    open fun rest() {}

}