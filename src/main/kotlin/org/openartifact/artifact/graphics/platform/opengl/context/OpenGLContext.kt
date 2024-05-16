/*
 * Copyright Artifact-Engine (c) 2024.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package org.openartifact.artifact.graphics.platform.opengl.context

import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL20.*
import org.openartifact.artifact.core.Artifact
import org.openartifact.artifact.graphics.interfaces.IContext
import org.openartifact.artifact.graphics.window.Window
import org.slf4j.LoggerFactory

class OpenGLContext(override var window : Window, var options : OpenGLContextOptions) : IContext {

    private val logger = LoggerFactory.getLogger(OpenGLContext::class.java)

    override fun preInit() {
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 6)
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)
    }

    override fun init() {
        glfwMakeContextCurrent(window.handle)

        GL.createCapabilities()

        glfwSetFramebufferSizeCallback(window.handle) { _ : Long, x : Int, y : Int ->
            glViewport(0, 0, x, y)
            if (options.redrawOnResize)
                swapBuffers()
        }

        logger.info("GPU Info (OpenGL):")
        logger.info("  Vendor: ${glGetString(GL_VENDOR)!!}")
        logger.info("  Renderer: ${glGetString(GL_RENDERER)!!}")
        logger.info("  OpenGL Version: ${glGetString(GL_VERSION)!!}")
        logger.info("  Primary GLSL Version: ${glGetString(GL_SHADING_LANGUAGE_VERSION)!!}")
    }

    override fun swapBuffers() {
        glEnable(GL_DEPTH_TEST)
        glDepthFunc(GL_LESS)

        glfwSwapBuffers(window.handle)
    }

    override fun shutdown() {

    }

    override fun push() {}
}