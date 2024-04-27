package org.openartifact.artifact.core.graphics

import org.openartifact.artifact.core.GameContext
import org.openartifact.artifact.core.graphics.window.Window
import org.openartifact.artifact.game.Node
import org.openartifact.artifact.core.graphics.node.NodeRenderer
import org.slf4j.LoggerFactory

class Graphics : Runnable {

    private val logger = LoggerFactory.getLogger(javaClass)

    private val nodeRenderer : Set<NodeRenderer<out Node>> = setOf()

    override fun run() {
        logger.debug("Creating window...")

        val context = GameContext.current()

        context.engine.window = Window(GameContext.current().windowProfile())
        context.engine.window.initWindow()
    }

}