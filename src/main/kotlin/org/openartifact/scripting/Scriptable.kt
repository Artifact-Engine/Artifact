package org.openartifact.scripting

/**
 * An interface to handle basic functions that are required to interact with the game and engine
 */
interface Scriptable {

    fun update()

    fun enable()

    fun disable()

}