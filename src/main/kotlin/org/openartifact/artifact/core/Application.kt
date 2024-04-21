package org.openartifact.artifact.core

import org.openartifact.artifact.game.scene.Scene
import java.io.File

class Application(val settings : ApplicationSettings, val scenes : Map<File, Scene>) {

    fun getScene(name : String) : Scene =
        scenes.values.find { it.sceneSettings.name == name } !!

    fun getCurrentScene() : Scene =
        getScene(settings.currentScene)

}