package org.openartifact.artifact.core.graphics.window

import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.system.MemoryUtil.NULL
import org.openartifact.artifact.core.Engine
import org.openartifact.artifact.core.event.events.KeyPressEvent
import org.openartifact.artifact.core.event.notify


class GLWindow : Window {

    private var window : Long = 0

    override fun initAPI() {
        GLFWErrorCallback.createPrint(System.err).set()

        require(glfwInit()) { "Failed to initialize GLFW" }

        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)

        window = glfwCreateWindow(800, 600, "Artifact <${Engine.application.settings.name}> OpenGL", NULL, NULL)

        require(window != NULL) { "Failed to create GLFW window" }

        glfwSetKeyCallback(
            window
        ) { window : Long, key : Int, scancode : Int, action : Int, mods : Int ->
            when (action) {
                GLFW_PRESS -> notify(KeyPressEvent(key))
            }
        }

        stackPush().use { stack ->
            val pWidth = stack.mallocInt(1)
            val pHeight = stack.mallocInt(1)

            glfwGetWindowSize(window, pWidth, pHeight)

            val vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor()) !!

            glfwSetWindowPos(
                window,
                (vidmode.width() - pWidth[0]) / 2,
                (vidmode.height() - pHeight[0]) / 2
            )
        }

        glfwMakeContextCurrent(window)
        glfwSwapInterval(1)
        glfwShowWindow(window)
    }

    override fun render() {
        GL.createCapabilities()

        glViewport(0, 0, 800, 600)

        glfwSetFramebufferSizeCallback(window) { _, width, height ->
            glViewport(0, 0, width, height)
        }

        glClearColor(0.0f, 0.0f, 0.2f, 1.0f)

        while (! glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

            processInput()

            Engine.application.update()

            glfwSwapBuffers(window)

            glfwPollEvents()
        }
    }

    /**
     * Unused because of easier handling using [glfwSetKeyCallback]
     */
    override fun processInput() {}

    override fun initWindow() {
        initAPI()
        render()

        Engine.application.rest()

        glfwFreeCallbacks(window)
        glfwDestroyWindow(window)

        glfwTerminate()
        glfwSetErrorCallback(null)?.free()
    }

}