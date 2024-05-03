package org.openartifact.artifact.graphics.platform.opengl

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL20.*
import org.openartifact.artifact.graphics.IContext
import org.openartifact.artifact.graphics.Window
import org.slf4j.LoggerFactory

class OpenGLContext(override var window : Window) : IContext {

    private val logger = LoggerFactory.getLogger(OpenGLContext::class.java)

    override fun preInit() {
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 6)
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)
    }

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
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

        glfwSwapBuffers(window.handle)
    }

    override fun shutdown() {

    }
}