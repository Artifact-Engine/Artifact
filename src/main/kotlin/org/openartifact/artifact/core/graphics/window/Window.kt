package org.openartifact.artifact.core.graphics.window

abstract class Window(open val profile : WindowProfile) {

    abstract fun initAPI()
    abstract fun render()
    abstract fun initWindow()

}