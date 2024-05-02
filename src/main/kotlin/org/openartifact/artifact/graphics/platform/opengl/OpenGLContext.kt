package org.openartifact.artifact.graphics.platform.opengl

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL20.GL_SHADING_LANGUAGE_VERSION
import org.openartifact.artifact.graphics.IContext
import org.openartifact.artifact.graphics.window.Window
import org.slf4j.LoggerFactory

internal class OpenGLContext(override var window : Window) : IContext {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun init() {
        glfwMakeContextCurrent(window.handle)

        GL.createCapabilities()

        logger.info("GPU Info:")
        logger.info("  Vendor: ${glGetString(GL_VENDOR)!!}")
        logger.info("  Renderer: ${glGetString(GL_RENDERER)!!}")
        logger.info("  OpenGL Version: ${glGetString(GL_VERSION)!!}")
        logger.info("  Primary GLSL Version: ${glGetString(GL_SHADING_LANGUAGE_VERSION)!!}")

    }

    override fun swapBuffers() {
        glfwSwapBuffers(window.handle)
    }

    override fun clear() {

    }

}