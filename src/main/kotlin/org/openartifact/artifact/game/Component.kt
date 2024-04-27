package org.openartifact.artifact.game

open class Component {

    var parent : Node? = null

    inline fun <reified T : Component> getComponent(): T? =
        parent?.components?.find { it is T } as? T

    val type = javaClass.simpleName

    open fun awake() {}
    open fun update() {}
    open fun rest() {}

}