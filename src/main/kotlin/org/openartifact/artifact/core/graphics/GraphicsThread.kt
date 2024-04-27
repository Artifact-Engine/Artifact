package org.openartifact.artifact.core.graphics

import org.openartifact.artifact.core.GameContext
import org.openartifact.artifact.core.graphics.window.GLWindow
import org.openartifact.artifact.core.graphics.window.Window
import org.openartifact.artifact.game.Node
import org.openartifact.artifact.game.NodeRenderer
import org.slf4j.LoggerFactory

class GraphicsThread : Thread("Graphics") {

    private val logger = LoggerFactory.getLogger(javaClass)

    private val nodeRenderer : Set<NodeRenderer<out Node>> = setOf()

    lateinit var window : Window

    override fun run() {
        logger.debug("Creating window...")
        window = when (GameContext.current().engineProfile().renderAPI) {
            RenderAPI.OpenGL -> GLWindow(GameContext.current().windowProfile())
        }
        window.initWindow()
    }

}