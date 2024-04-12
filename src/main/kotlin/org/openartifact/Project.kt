package org.openartifact

import org.slf4j.Logger
import org.slf4j.LoggerFactory

data class Project(val name: String) {

    private var logger: Logger = LoggerFactory.getLogger(name)

    fun initialize() {
        logger.info("Loading Artifact Project: $name")

        if (Engine.engineState != Engine.EngineState.Initialized)
            throw IllegalStateException("Engine not initialized.")
    }

}