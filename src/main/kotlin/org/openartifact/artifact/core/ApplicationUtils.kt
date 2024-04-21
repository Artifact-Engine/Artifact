package org.openartifact.artifact.core

import com.google.gson.Gson
import org.openartifact.artifact.game.scene.Scene
import org.openartifact.artifact.game.scene.readScene
import org.openartifact.artifact.utils.requireDirectory
import org.openartifact.artifact.utils.requireFile
import org.slf4j.LoggerFactory
import java.io.File

private val logger = LoggerFactory.getLogger("ApplicationReader")

fun readApplicationFromFile(file : File) : Application {

    val files = file.listFiles()

    val srcDir = requireDirectory(file, "src")
    val mainDir = requireDirectory(srcDir, "main")
    val resourcesDir = requireDirectory(mainDir, "resources")

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

        logger.debug("Reading '${sceneFile.name}'...")

        scenes[sceneFile] = readScene(sceneFile)
    }

    val settingsFile = requireFile(resourcesDir, "settings.json")

    val applicationSettings = readApplicationSettings(settingsFile)

    return Application(applicationSettings, scenes, file)
}

private fun readApplicationSettings(file : File): ApplicationSettings =
    Gson().fromJson(file.readText(), ApplicationSettings::class.java)