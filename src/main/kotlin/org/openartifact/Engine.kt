package org.openartifact

import org.openartifact.configuration.initializeFileStructure
import org.openartifact.rendering.Renderer
import org.openartifact.rendering.renderers.DefaultRenderer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Engine(project: Project) {

    companion object {
        lateinit var gameWindow: Window
        lateinit var renderer: Renderer

        var engineState = EngineState.UNKNOWN
    }

    private var logger: Logger = LoggerFactory.getLogger("Artifact Engine")

    init {
        logger.info("Initializing Artifact Engine")
        engineState = EngineState.INITIALIZING

        logger.info("Initializing renderer")
        renderer = DefaultRenderer()

        logger.info("Initializing Game Window")
        gameWindow = Window(1024, 768, "<Artifact Engine - OpenGL>")

        logger.info("Loading File Structure")
        initializeFileStructure()

        engineState = EngineState.INITIALIZED
        
        project.initialize()
    }

    enum class EngineState {
        UNKNOWN,
        INITIALIZING,
        INITIALIZED
    }

}