package org.openartifact.artifact.core

import org.openartifact.artifact.core.rendering.GraphicsThread
import org.slf4j.LoggerFactory

object Engine {

    private val logger = LoggerFactory.getLogger(javaClass)

    lateinit var application : Application
    lateinit var graphicsThread : GraphicsThread

    private const val BASE_PKG = "org.openartifact.artifact"
    const val NODE_PGK = "$BASE_PKG.game.nodes"
    const val COMPONENT_PGK = "$BASE_PKG.game.components"

    init {
        logger.debug("Reading application...")

        application = Application()

        logger.debug("Detected rendering API: ${application.settings.rendererType}")

        logger.debug("Launching Graphics Thread")
        graphicsThread = GraphicsThread()
        graphicsThread.start()

        val scene = application.getCurrentScene()

        println(scene.nodes)
    }

}