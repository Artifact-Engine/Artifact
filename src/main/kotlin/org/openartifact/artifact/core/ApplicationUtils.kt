package org.openartifact.artifact.core

import org.openartifact.artifact.game.scene.Scene
import org.openartifact.artifact.game.scene.validateSceneStructure
import org.openartifact.artifact.utils.requireDirectory
import org.slf4j.LoggerFactory
import java.io.File

private val logger = LoggerFactory.getLogger("ApplicationReader")

private fun validateResources(file : File) {

}

fun readApplicationFromFile(file : File) : Application {

    val files = file.listFiles()

    val srcDir = requireDirectory(file, "src")
    val resourcesDir = requireDirectory(file, "resources")

    // Validate resources
    val scenesDir = requireDirectory(resourcesDir, "scenes")

    val scenesFiles = scenesDir.listFiles()

    logger.info("Found scenes: ${scenesFiles !!.size}")
    logger.debug("Scenes: ${scenesFiles.map { it.name }}")

    require(scenesFiles.isNotEmpty()) { "Scenes directory needs at least one scene!" }

    logger.info("Trying to parse scenes...")

    val scenes : MutableMap<File, Scene> = mutableMapOf()

    scenesFiles.forEach { sceneFile ->
        require(sceneFile.isDirectory) { "Scene '${sceneFile.name}' must be a directory!" }

        validateSceneStructure(sceneFile)

        logger.debug("Scene '${sceneFile.name}' passed validation tests.")
        logger.debug("Reading '${sceneFile.name}'...")

        //scenes[sceneFile] = readScene(sceneFile)
    }

    return Application(scenes)
}