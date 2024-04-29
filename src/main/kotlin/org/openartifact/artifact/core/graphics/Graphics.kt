package org.openartifact.artifact.core.graphics

import org.openartifact.artifact.core.GameContext
import org.openartifact.artifact.core.graphics.window.Window
import org.slf4j.LoggerFactory

class Graphics : Runnable {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun run() {
        logger.debug("Creating window...")

        val context = GameContext.current()

        context.engine.window = Window(GameContext.current().windowProfile())
        context.engine.window.initWindow()
    }

}