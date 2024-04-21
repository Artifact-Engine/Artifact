package org.openartifact.artifact.core.rendering

import org.openartifact.artifact.core.Engine.application
import org.openartifact.artifact.core.rendering.window.GLWindow
import org.openartifact.artifact.core.rendering.window.Window
import org.slf4j.LoggerFactory

class GraphicsThread : Thread() {

    private val logger = LoggerFactory.getLogger(javaClass)

    lateinit var window: Window

    override fun run() {
        logger.debug("Creating window...")
        window = when (application.settings.rendererType) {
            RendererType.OpenGL -> GLWindow()
        }
        window.initWindow()
    }

}