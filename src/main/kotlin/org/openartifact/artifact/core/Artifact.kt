package org.openartifact.artifact.core

import org.openartifact.artifact.graphics.window.Window
import org.openartifact.artifact.graphics.window.WindowConfig

class Artifact(val application: Application) {

    private lateinit var _window : Window

    fun init(windowConfig: WindowConfig) {
        _window = Window(windowConfig)
        _window.run()
    }

    val window: Window
        get() = _window

    companion object {
        @Volatile
        private var _instance: Artifact? = null

        val instance: Artifact
            get() = _instance?: synchronized(this) {
                _instance?: throw IllegalStateException("Artifact instance not initialized")
            }

        fun launch(application: Application): Artifact {
            return Artifact(application).also {
                _instance = it
                _instance!!.init(application.windowConfig)
            }
        }
    }
}
