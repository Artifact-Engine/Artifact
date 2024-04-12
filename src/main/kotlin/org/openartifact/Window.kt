package org.openartifact

import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL.*
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil
import org.openartifact.configuration.vSync
import org.openartifact.scripting.event.events.EventKeyAction
import org.openartifact.scripting.event.notify

data class Window(val width: Int, val height: Int, val title: String, var window: Long = 0) {

    init {
        GraphicsThread().start()
    }

    private fun shutdown() {
        Engine.renderer.shutdown()
        Callbacks.glfwFreeCallbacks(window)
        glfwDestroyWindow(window)
        glfwTerminate()
        glfwSetErrorCallback(null)?.free()
    }

    inner class GraphicsThread : Thread("Graphics") {

        /**
         * Initializes GLFW and sets up the window
         */
        private fun initializeGLFW() {
            GLFWErrorCallback.createPrint(System.err).set()
            check(glfwInit()) { "Failed to initialize GLFW" }
            glfwDefaultWindowHints()
            glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
            glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)

            window = glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL)
            require(window != MemoryUtil.NULL) { "Failed to create the GLFW window" }

            glfwSetKeyCallback(window) { _, key, scancode, action, mods ->
                notify(EventKeyAction(key, scancode, action, mods))
            }

            centerWindow()
            glfwMakeContextCurrent(window)

            glfwSwapInterval(if (vSync) 1 else 0)

            glfwShowWindow(window)
        }

        /**
         * Centers the window to the screen
         */
        private fun centerWindow() {
            MemoryStack.stackPush().use { stack ->
                val pWidth = stack.mallocInt(1)
                val pHeight = stack.mallocInt(1)
                glfwGetWindowSize(window, pWidth, pHeight)
                val vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor())
                glfwSetWindowPos(
                    window,
                    (vidmode!!.width() - pWidth[0]) / 2,
                    (vidmode.height() - pHeight[0]) / 2
                )
            }
        }

        private fun render() {
            createCapabilities()
            Engine.renderer.init() // Initialize the renderer

            while (!glfwWindowShouldClose(window)) {
                update()

                glfwSwapBuffers(window)
                glfwPollEvents()
            }
        }

        /**
         * Gets called every frame
         * @see render
         */
        private fun update() {
            Engine.renderer.render()
            Engine.scriptables.forEach { it.update() }
        }

        override fun run() {
            initializeGLFW()
            render()
            shutdown()
        }
    }
}
