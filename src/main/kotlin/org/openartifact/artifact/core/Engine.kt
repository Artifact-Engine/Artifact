package org.openartifact.artifact.core

import org.openartifact.artifact.core.rendering.RendererType
import org.openartifact.artifact.core.rendering.window.GLWindow
import org.openartifact.artifact.core.rendering.window.Window
import org.slf4j.LoggerFactory
import java.io.File

object Engine {

    private val logger = LoggerFactory.getLogger(javaClass)

    lateinit var application : Application
    lateinit var window: Window

    private const val BASE_PKG = "org.openartifact.artifact"
    const val NODE_PGK = "$BASE_PKG.game.nodes"
    const val COMPONENT_PGK = "$BASE_PKG.game.components"

    fun init(projectFile : File) {
        logger.info("Reading application...")

        application = readApplicationFromFile(projectFile)

        logger.debug("EngineApp: ${application.settings.mainClass}")

        //engineApp = createInstance<EngineApp>(application.settings.mainClass)!!

        //engineApp.boot()

        logger.debug("Detected rendering API: ${application.settings.rendererType}")

        logger.debug("Creating window...")
        window = when (application.settings.rendererType) {
            RendererType.OpenGL -> GLWindow()
        }

        val scene = application.getCurrentScene()

        println(scene.nodes)
    }

}