package org.openartifact.artifact.core

class GameContext private constructor(private val applicationProfile : ApplicationProfile) {

    private var application : Application? = null
    internal val engine = Engine()

    fun launch() : GameContext {
        application = engine.loadApplication(applicationProfile)
        engine.loadGraphics()
        return this
    }

    fun setCurrent() : GameContext {
        require(current != this) { "GameContext was already set to current." }
        current = this
        return this
    }

    fun application() : Application =
        application ?: throw IllegalStateException("No application found. Was the engine initialized?")

    companion object {

        private var current : GameContext? = null

        fun createContext(block : Builder.() -> Unit) : GameContext {
            val builder = Builder()
            block(builder)
            val context = GameContext(builder.applicationProfile!!)
            return context
        }

        fun current() : GameContext =
            current ?: throw IllegalStateException("No current GameContext exists. Try using setCurrent.")
    }

    class Builder {
        var applicationProfile: ApplicationProfile? = null

        fun configureApplicationProfile(profile: ApplicationProfile) {
            applicationProfile = profile
        }
    }

}