package org.openartifact.artifact.graphics.interfaces

import org.openartifact.artifact.graphics.IRendererComponent
import org.openartifact.artifact.graphics.Window

interface IContext : IRendererComponent {

    open var window : Window
    
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