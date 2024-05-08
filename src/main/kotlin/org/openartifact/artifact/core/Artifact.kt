package org.openartifact.artifact.core

import org.openartifact.artifact.graphics.Window

class Artifact(val application: Application) {

    private lateinit var _window : Window

    fun init() {
        _window = Window()
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
                _instance!!.init()
            }
        }
    }
}
