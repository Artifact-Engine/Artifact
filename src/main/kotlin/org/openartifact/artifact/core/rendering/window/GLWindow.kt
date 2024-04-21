package org.openartifact.artifact.core.rendering.window

import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.system.MemoryUtil.NULL
import org.openartifact.artifact.core.Engine


class GLWindow : Window {

    private var window : Long = 0

    override fun init() {
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
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) glfwSetWindowShouldClose(
                window,
                true
            )
        }

        stackPush().use { stack ->
            val pWidth = stack.mallocInt(1)
            val pHeight = stack.mallocInt(1)

            glfwGetWindowSize(window, pWidth, pHeight)

            val vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor())!!

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
            println("Adjusting to $width:$height")
            glViewport(0, 0, width, height)
        }

        glClearColor(0.0f, 0.0f, 0.2f, 1.0f)

        while (! glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

            glfwSwapBuffers(window)

            glfwPollEvents()
        }
    }

    init {
        init()
        render()

        glfwFreeCallbacks(window)
        glfwDestroyWindow(window)

        glfwTerminate()
        glfwSetErrorCallback(null)?.free()
    }

}