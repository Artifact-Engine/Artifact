package org.openartifact.artifact.graphics

import org.openartifact.artifact.graphics.window.Window

internal interface IContext {

    var window : Window

    fun init()
    fun swapBuffers()
    fun clear()

}