package org.openartifact.artifact.core

import com.google.gson.Gson
import org.openartifact.artifact.game.scene.Scene
import org.openartifact.artifact.game.scene.readScene
import org.slf4j.LoggerFactory
import java.io.File
import java.io.InputStream
import kotlin.io.path.toPath

private val logger = LoggerFactory.getLogger("ApplicationReader")

class Application() {

    val settings: ApplicationSettings
    val scenes: Map<File, Scene>

    init {
        // Load scenes from the resources directory
        val scenesDirPath = "/scenes"
        val scenesDir = Engine::class.java.getResource(scenesDirPath)
            ?: throw IllegalStateException("Scenes directory not found in resources!")

        val scenesFiles = scenesDir.toURI().toPath().toFile().listFiles()
            ?: throw IllegalStateException("Scenes directory needs at least one scene!")

        logger.info("Found scenes: ${scenesFiles.size}")
        logger.debug("Scenes: ${scenesFiles.map { it.name }}")

        val scenesMap: MutableMap<File, Scene> = mutableMapOf()

        scenesFiles.forEach { sceneFile ->
            require(sceneFile.isDirectory) { "Scene '${sceneFile.name}' must be a directory!" }

            logger.debug("Reading '${sceneFile.name}'...")

            scenesMap[sceneFile] = readScene(sceneFile)
        }

        // Load application settings from the resources directory
        val settingsFilePath = "/settings.json"
        val settingsFileStream = Engine::class.java.getResourceAsStream(settingsFilePath)
            ?: throw IllegalStateException("Settings file not found in resources!")

        val applicationSettings = readApplicationSettings(settingsFileStream)

        // Initialize the scenes and settings properties
        this.settings = applicationSettings
        this.scenes = scenesMap
    }

    private fun readApplicationSettings(inputStream: InputStream): ApplicationSettings =
        Gson().fromJson(inputStream.bufferedReader(), ApplicationSettings::class.java)

    fun getScene(name: String): Scene =
        scenes.values.find { it.sceneSettings.name == name } ?: throw IllegalArgumentException("Scene not found: $name")

    fun getCurrentScene(): Scene =
        getScene(settings.currentScene)
}
