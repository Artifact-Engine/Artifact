package org.openartifact.artifact.core

class GameContext private constructor(private val applicationProfile : ApplicationProfile) {

    private lateinit var application : Application
    internal val engine = Engine()

    fun launch() {
        application = engine.loadApplication(applicationProfile)
        engine.loadGraphics()
    }

    fun setCurrent() : GameContext {
        current = this
        return this
    }

    fun application() : Application =
        application

    companion object {

        private var current : GameContext? = null

        fun createContext(block : GameContext.Builder.() -> Unit) : GameContext {
            val builder = Builder()
            block(builder)
            val context = GameContext(builder.applicationProfile!!)
            return context
        }

        fun current() : GameContext =
            current!!
    }

    class Builder {
        var applicationProfile: ApplicationProfile? = null

        fun configureApplicationProfile(profile: ApplicationProfile) {
            applicationProfile = profile
        }
    }

}