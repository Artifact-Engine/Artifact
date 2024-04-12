package org.openartifact

import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL.*
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil
import org.openartifact.scripting.event.events.EventKeyAction
import org.openartifact.scripting.event.notify


data class Window(val width: Int, val height: Int, val title: String) {

    var window: Long = -1

    init {
        GraphicsThread().start()
    }

    private fun shutdown() {
        Engine.renderer.shutdown()

        // Free the window callbacks and destroy the window
        Callbacks.glfwFreeCallbacks(window)
        glfwDestroyWindow(window)

        // Terminate GLFW and free the error callback
        glfwTerminate()
        glfwSetErrorCallback(null)!!.free()
    }

    inner class GraphicsThread: Thread("Graphics") {

        private fun initializeGLFW() {
            // Setup an error callback. The default implementation
            // will print the error message in System.err.
            GLFWErrorCallback.createPrint(System.err).set()

            // Initialize  Most GLFW functions will not work before doing this.
            check(glfwInit()) { "Failed to initialize GLFW" }

            // Configure GLFW
            glfwDefaultWindowHints() // optional, the current window hints are already the default
            glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE) // the window will stay hidden after creation
            glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE) // the window will be resizable

            // Create the window
            window = glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL)
            if (window == MemoryUtil.NULL) throw RuntimeException("Failed to create the GLFW window")

            // Setup a key callback. It will be called every time a key is pressed, repeated or released.
            glfwSetKeyCallback(window) { window: Long, key: Int, scancode: Int, action: Int, mods: Int ->
                notify(event = EventKeyAction(key, scancode, action, mods))
            }

            MemoryStack.stackPush().use { stack ->
                val pWidth = stack.mallocInt(1) // int*
                val pHeight = stack.mallocInt(1) // int*

                // Get the window size passed to glfwCreateWindow
                glfwGetWindowSize(window, pWidth, pHeight)

                // Get the resolution of the primary monitor
                val vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor())

                // Center the window
                glfwSetWindowPos(
                    window,
                    (vidmode!!.width() - pWidth[0]) / 2,
                    (vidmode.height() - pHeight[0]) / 2
                )
            }

            // Make the OpenGL context current
            glfwMakeContextCurrent(window)
            // Enable v-sync
            glfwSwapInterval(1)

            // Make the window visible
            glfwShowWindow(window)
        }

        private fun render() {
            // This line is critical for LWJGL's interoperation with GLFW's
            // OpenGL context, or any context that is managed externally.
            // LWJGL detects the context that is current in the current thread,
            // creates the GLCapabilities instance and makes the OpenGL
            // bindings available for use.
            createCapabilities()

            Engine.renderer.init()

            // Run the rendering loop until the user has attempted to close
            // the window or has pressed the ESCAPE key.
            while (!glfwWindowShouldClose(window)) {
                Engine.renderer.render()
                Engine.scriptables.forEach { it.update() }

                glfwSwapBuffers(window) // swap the color buffers

                // Poll for window events. The key callback above will only be
                // invoked during this call.
                glfwPollEvents()
            }
        }

        override fun run() {
            initializeGLFW()
            render()
        }

    }

}