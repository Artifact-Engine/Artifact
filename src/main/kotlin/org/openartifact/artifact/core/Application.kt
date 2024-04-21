package org.openartifact.artifact.core

import com.google.gson.Gson
import org.openartifact.artifact.game.scene.Scene
import org.openartifact.artifact.game.scene.readScene
import org.openartifact.artifact.utils.requireDirectory
import org.openartifact.artifact.utils.requireFile
import org.slf4j.LoggerFactory
import java.io.File

private val logger = LoggerFactory.getLogger("ApplicationReader")

class Application() {

    val settings : ApplicationSettings
    val scenes : HashMap<File, Scene>

    private var activeScene : Scene? = null

    init {
        val rootDir = File(Engine::class.java.getResource("/game").file)

        val scenesDir = requireDirectory(rootDir, "scenes")

        val scenesFiles = scenesDir.listFiles()
            ?: throw IllegalStateException("Scenes directory needs at least one scene!")

        logger.info("Found scenes: ${scenesFiles.size}")
        logger.debug("Scenes: ${scenesFiles.map { it.name }}")

        val scenesMap : HashMap<File, Scene> = hashMapOf()

        scenesFiles.forEach { sceneFile ->
            requireDirectory(sceneFile)
            require(sceneFile.isDirectory) { "Scene '${sceneFile.name}' must be a directory!" }

            logger.debug("Reading '${sceneFile.name}'...")

            scenesMap[sceneFile] = readScene(sceneFile)
        }

        val settingsFile = requireFile(rootDir, "settings.json")

        val applicationSettings = readApplicationSettings(settingsFile)

        this.settings = applicationSettings
        this.scenes = scenesMap

        activeScene = getScene(settings.currentScene)
        activeScene?.load()
    }

    private fun readApplicationSettings(file : File) : ApplicationSettings =
        Gson().fromJson(file.readText(), ApplicationSettings::class.java)

    fun writeApplicationSettings(applicationSettings : ApplicationSettings) : String =
        Gson().toJson(applicationSettings, ApplicationSettings::class.java)

    fun getScene(name : String) : Scene =
        scenes.values.find { it.sceneSettings.name == name } ?: throw IllegalArgumentException("Scene not found: $name")

    fun update() {
        activeScene?.update()
    }

    fun rest() {
        activeScene?.rest()
    }

    fun getActiveScene() : Scene =
        activeScene !!

    fun switchScene(scene : String) {
        activeScene?.rest()
        activeScene = getScene(scene)
        activeScene?.load()
    }
}
