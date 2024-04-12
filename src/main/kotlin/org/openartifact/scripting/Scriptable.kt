package org.openartifact.scripting

import org.openartifact.node.Node

/**
 * An interface to handle basic functions that are required to interact with the game and engine
 */
open class Scriptable<T: Node> {

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

}