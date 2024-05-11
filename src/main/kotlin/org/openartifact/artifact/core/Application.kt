package org.openartifact.artifact.core

import org.openartifact.artifact.graphics.RenderAPI
import org.openartifact.artifact.graphics.Renderer
import org.openartifact.artifact.graphics.platform.opengl.OpenGLRenderer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

open class Application(val api : RenderAPI) {

    open val logger : Logger = LoggerFactory.getLogger(javaClass)

    lateinit var renderer : Renderer

    fun createRenderer() : Renderer {
        return when (api) {
            RenderAPI.OpenGL -> OpenGLRenderer()
            RenderAPI.Vulkan -> TODO()
        }
    }

    open fun init() {}
    open fun update() {}
    open fun shutdown() {}

}