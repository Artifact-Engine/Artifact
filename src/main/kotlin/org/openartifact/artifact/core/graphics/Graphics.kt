package org.openartifact.artifact.core.graphics

import org.openartifact.artifact.core.GameContext
import org.openartifact.artifact.core.graphics.window.Window
import org.slf4j.LoggerFactory

class Graphics : Runnable {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun run() {
        logger.debug("Creating window...")

        val gameContext = GameContext.current()

        gameContext.engine.window = Window(GameContext.current().windowProfile())
        gameContext.engine.window.initWindow()
    }

}