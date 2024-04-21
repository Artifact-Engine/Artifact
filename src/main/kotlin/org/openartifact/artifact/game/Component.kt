package org.openartifact.artifact.game

open class Component {

    val type = javaClass.simpleName

    open fun awake() {}
    open fun update() {}
    open fun rest() {}

}