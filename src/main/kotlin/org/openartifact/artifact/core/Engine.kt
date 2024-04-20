package org.openartifact.artifact.core

import org.slf4j.LoggerFactory
import java.io.File

object Engine {

    private val logger = LoggerFactory.getLogger(javaClass)

    lateinit var application : Application

    private const val BASE_PKG = "org.openartifact.artifact"
    const val NODE_PGK = "$BASE_PKG.game.nodes"
    const val COMPONENT_PGK = "$BASE_PKG.game.components"

    fun init(projectFile : File) {
        logger.info("Trying to read application...")

        application = readApplicationFromFile(projectFile)
    }

}