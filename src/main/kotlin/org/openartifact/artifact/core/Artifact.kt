package org.openartifact.artifact.core

import org.openartifact.artifact.graphics.Window

class Artifact(val application : Application) {

    lateinit var window : Window

    fun init() : Artifact {
        window = Window()
        window.run()
        return this
    }

    companion object {

        private var current : Artifact? = null

        fun create(application: Application): Artifact {
            val artifact = Artifact(application)
            current = artifact
            return artifact
        }

        fun current() : Artifact =
            current!!

    }
}