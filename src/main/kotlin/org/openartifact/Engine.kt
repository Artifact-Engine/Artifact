package org.openartifact

import org.openartifact.configuration.initializeFileStructure
import org.openartifact.rendering.Renderer
import org.openartifact.rendering.renderers.DefaultRenderer
import org.openartifact.scripting.Scriptable
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Engine {

    var gameWindow: Window
    var renderer: Renderer

    var engineState = EngineState.Unknown

    var scriptables = mutableListOf<Scriptable<*>>()

    private var logger: Logger = LoggerFactory.getLogger("Artifact Engine")

    init {
        logger.info("Initializing Artifact Engine")

        logger.info("Initializing File Structure")
        initializeFileStructure()

        logger.info("Initializing Graphics")
        logger.info("Initializing Renderer")
        renderer = DefaultRenderer()

        logger.info("Initializing Game Window")
        gameWindow = Window(1024, 768, "<Artifact Engine - OpenGL>")

        logger.info("Loading Scene")
        println(scriptables)

        logger.info("Starting Graphics Thread")


        engineState = EngineState.Initialized
    }

    enum class EngineState {
        Unknown,
        Initialized
    }

}