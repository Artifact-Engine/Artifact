package org.openartifact.artifact.core

import org.openartifact.artifact.game.scene.Scene
import org.openartifact.artifact.game.scene.SceneManager

class Application(val profile : ApplicationProfile, val sceneManager : SceneManager) {

    private var activeScene : Scene? = null

    init {
        activeScene = sceneManager.getScene(profile.startingSceneId)
        activeScene?.load()
    }

    fun update() {
        activeScene?.update()
    }

    fun rest() {
        activeScene?.rest()
    }
}
