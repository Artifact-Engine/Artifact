package org.openartifact.rendering.renderers

import org.lwjgl.opengl.GL30.*
import org.openartifact.core.Engine
import org.openartifact.rendering.Renderer

class SimpleRenderer: Renderer {

    override fun init() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        Engine.application.currentScene?.nodes?.forEach { it.renderer?.init() }
    }

    override fun render() {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT) // clear the framebuffer

        Engine.application.currentScene?.nodes?.forEach { it.renderer?.render() }
    }


    override fun shutdown() {
        Engine.application.currentScene?.nodes?.forEach { it.renderer?.shutdown() }
    }

}
