package org.openartifact.core

import org.openartifact.configuration.initializeFileStructure
import org.openartifact.core.application.Application
import org.openartifact.rendering.Renderer
import org.openartifact.rendering.renderers.SimpleRenderer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Engine {

    var gameWindow: Window
    var renderer: Renderer

    lateinit var application: Application

    private var logger: Logger = LoggerFactory.getLogger(javaClass)

    init {
        logger.info("Initializing Artifact Engine")

        logger.info("Initializing File Structure")
        initializeFileStructure()

        logger.info("Initializing Graphics")
        renderer = SimpleRenderer()

        logger.info("Initializing Game Window")
        gameWindow = Window(1024, 768, "<Artifact Engine - OpenGL>")
    }

}