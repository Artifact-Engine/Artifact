package org.openartifact

import org.openartifact.rendering.Renderer
import org.openartifact.rendering.renderers.DefaultRenderer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Artifact {

    lateinit var gameWindow: Window
    lateinit var renderer: Renderer

    /**
     * Initializes the window etc
     * @see Window
     */
    fun initialize() {
        renderer = DefaultRenderer()

        gameWindow = Window(1024, 768, "<Artifact Engine - OpenGL>")
        gameWindow.initializeWindow()

        initializeStructure()
    }

    fun getLogger(name: String): Logger =
        LoggerFactory.getLogger("Artifact -> $name")

}