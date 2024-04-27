package org.openartifact.artifact.game.scene

import org.openartifact.artifact.utils.requireDirectory
import org.slf4j.LoggerFactory
import java.io.File

class SceneManager {

    private val logger = LoggerFactory.getLogger(this::class.java)
    private val scenes = hashMapOf<File, Scene>()
    var activeScene: Scene? = null

    fun loadScenesFromFile(scenesDir: File) {
        require(scenesDir.listFiles()?.isNotEmpty() == true) { "Scenes directory needs to contain at least one scene!" }
        scenesDir.listFiles()?.forEach { sceneFile ->
            requireDirectory(sceneFile)
            require(sceneFile.isDirectory) { "Scene '${sceneFile.name}' must be a directory!" }

            logger.debug("Reading '${sceneFile.name}'...")
            scenes[sceneFile] = readScene(sceneFile)
        }
        logger.debug("Found scenes: ${scenes.size}")
        logger.debug("Scenes: {}", scenes.keys.joinToString { it.name })
    }

    fun getScene(name: String): Scene? = scenes.values.firstOrNull { it.profile.name == name }

    fun switchScene(scene: Scene) {
        activeScene?.rest()
        activeScene = scene
        activeScene?.load()
    }

    fun switchScene(name: String) {
        switchScene(getScene(name) ?: throw IllegalArgumentException("Scene not found: $name"))
    }
}
