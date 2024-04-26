package org.openartifact.artifact.core

import org.openartifact.artifact.game.scene.SceneManager
import org.slf4j.LoggerFactory

class GameContext private constructor(builder: Builder) {

    private val logger = LoggerFactory.getLogger("GameContext")
    lateinit var application: Application

    companion object {

        private var currentContext: GameContext? = null

        fun createContext(block: Builder.() -> Unit): GameContext {
            println(currentContext == null)
            if (currentContext == null) {
                println("OK")
                currentContext = Builder().apply(block).build()

                currentContext!!.application = Engine.loadApplication()
                Engine.loadGraphics()

                println(currentContext!!.application.sceneManager.scenes)
            }
            return currentContext!!
        }

        fun getCurrentContext(): GameContext {
            return currentContext ?: throw IllegalStateException("GameContext has not been initialized yet.")
        }
    }

    class Builder {

        lateinit var profile: ApplicationProfile

        fun configureApplicationProfile(profile: ApplicationProfile) {
            this.profile = profile
        }

        fun build(): GameContext {
            return GameContext(this)
        }
    }
}
