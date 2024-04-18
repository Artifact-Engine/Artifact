package org.openartifact.rendering

/**
 * Defines methods a render needs to function.
 */
interface Renderer {

    fun init()

    fun render()

    fun shutdown()

}