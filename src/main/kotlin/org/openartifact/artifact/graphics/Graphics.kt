package org.openartifact.artifact.graphics

import org.lwjgl.opengl.GL11
import org.openartifact.artifact.core.Application
import org.openartifact.artifact.graphics.window.Window
import org.slf4j.LoggerFactory

class Graphics : Runnable {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun run() {
        logger.debug("Creating window...")

        val application = Application.current()

        application.engine.window = Window(Application.current().windowProfile())
        application.engine.window.initWindow()
    }

}