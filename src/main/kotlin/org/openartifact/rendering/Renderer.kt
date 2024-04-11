package org.openartifact.rendering

/**
 * Defines functions a render needs to function.
 */
interface Renderer {

    fun init()
    fun render()
    fun shutdown()

}