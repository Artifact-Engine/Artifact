package org.openartifact.scripting

import org.openartifact.node.Component
import org.openartifact.node.Node

/**
 * An interface to handle basic functions that are required to interact with the game and engine
 */
open class Scriptable(val nodeParent: Node): Component() {

    /**
     * Gets called every time the game performs a tick
     */
    open fun update() {}

    /**
     * Gets called every time the [T] node was enabled
     */
    open fun enable() {}

    /**
     * Gets called every time the [T] node was enabled
     */
    open fun disable() {}

    /**
     * Gets called after the node was loaded
     */
    open fun start() {}

    /**
     * Returns a component
     */
    inline fun <reified T: Component>getComponent(): Component? {
        return nodeParent.components.find { it is T }
    }

}