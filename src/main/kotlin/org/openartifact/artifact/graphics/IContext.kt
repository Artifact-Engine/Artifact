package org.openartifact.artifact.graphics

interface IContext {

    var window : Window

    /**
     * Called before the window was initialized.
     */
    fun preInit()

    /**
     * Called after the window was initialized.
     */
    fun init()

    fun swapBuffers()
    fun shutdown()

}