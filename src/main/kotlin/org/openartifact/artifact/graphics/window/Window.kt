/*
 * Copyright Artifact-Engine (c) 2024.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package org.openartifact.artifact.graphics.window

import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil.NULL
import org.openartifact.artifact.core.Artifact
import org.openartifact.artifact.core.event.events.FPSUpdateEvent
import org.openartifact.artifact.core.event.deploy
import org.openartifact.artifact.graphics.RenderAPI
import org.openartifact.artifact.graphics.interfaces.IContext
import org.openartifact.artifact.graphics.platform.opengl.context.OpenGLContext
import org.openartifact.artifact.graphics.platform.opengl.context.OpenGLContextOptions
import org.openartifact.artifact.input.MouseInput
import org.openartifact.artifact.timeInit
import org.slf4j.LoggerFactory

class Window(val windowConfig : WindowConfig) {

    var handle : Long = 0
    private lateinit var context : IContext

    private val logger = LoggerFactory.getLogger(javaClass)

    fun run() {
        logger.info("Initializing window...")
        init()
        Artifact.instance.application.init()

        logger.info("Engine startup took: ~${(System.currentTimeMillis() - timeInit)}ms")

        update()
        Artifact.instance.application.shutdown()
        shutdown()
    }

    private fun init() {
        GLFWErrorCallback.createPrint(System.err).set()

        require(glfwInit()) { "Failed to initialize GLFW" }

        context = when (Artifact.instance.application.api) {
            RenderAPI.OpenGL -> OpenGLContext(this, OpenGLContextOptions(true))
            RenderAPI.Vulkan -> TODO()
        }

        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)

        context.preInit()

        handle = glfwCreateWindow(windowConfig.width.toInt(), windowConfig.height.toInt(), windowConfig.title, NULL, NULL)

        if (handle == NULL) throw RuntimeException("Failed to create the GLFW window")

        context.init()

        // Center the window on the screen
        MemoryStack.stackPush().use { stack ->
            val pWidth = stack.mallocInt(1)
            val pHeight = stack.mallocInt(1)

            glfwGetWindowSize(handle, pWidth, pHeight)

            val vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor())!!

            glfwSetWindowPos(
                handle,
                (vidmode.width() - pWidth.get(0)) / 2,
                (vidmode.height() - pHeight.get(0)) / 2
            )
        }

        glfwSwapInterval(1)
        glfwShowWindow(handle)

        MouseInput.initialize(handle)
    }

    private fun update() {
        var lastTime = System.nanoTime()
        var lastFpsTime = System.nanoTime()
        var fps = 0

        while (!glfwWindowShouldClose(handle)) {
            val currentTime = System.nanoTime()
            val deltaTime = (currentTime - lastTime) / 1_000_000_000.0

            lastTime = currentTime

            context.swapBuffers()

            Artifact.instance.application.update(deltaTime)

            fps++
            if (currentTime - lastFpsTime >= 1_000_000_000) {
                deploy(FPSUpdateEvent(fps))
                fps = 0
                lastFpsTime += 1_000_000_000
            }

            glfwPollEvents()
        }
    }

    private fun shutdown() {
        glfwFreeCallbacks(handle)
        glfwDestroyWindow(handle)

        glfwTerminate()
        glfwSetErrorCallback(null)?.free()
    }

}