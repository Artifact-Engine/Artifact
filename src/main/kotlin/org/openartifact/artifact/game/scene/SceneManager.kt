package org.openartifact.artifact.game.scene

import org.openartifact.artifact.utils.requireDirectory
import org.slf4j.LoggerFactory
import java.io.File

class SceneManager {

    private val logger = LoggerFactory.getLogger(this::class.java)
    val scenes : HashMap<File, Scene> = hashMapOf()

    private var activeScene : Scene? = null

    fun loadScenes(scenesDir : File) {
        val scenesFiles = scenesDir.listFiles()
            ?: throw IllegalStateException("Scenes directory needs to contain least one scene!")

        logger.info("Found scenes: ${scenesFiles.size}")
        logger.debug("Scenes: {}", scenesFiles.map { it.name })

        scenesFiles.forEach { sceneFile ->
            requireDirectory(sceneFile)
            require(sceneFile.isDirectory) { "Scene '${sceneFile.name}' must be a directory!" }

            logger.debug("Reading '${sceneFile.name}'...")

            scenes[sceneFile] = readScene(sceneFile)
        }
    }

    fun getScene(name : String) : Scene? =
        scenes.values.firstOrNull { it.sceneSettings.name == name }

    fun switchScene(scene : Scene) {
        activeScene?.rest()
        activeScene = scene
        activeScene?.load()
    }

    fun switchScene(name : String) {
        switchScene(getScene(name) !!)
    }

}