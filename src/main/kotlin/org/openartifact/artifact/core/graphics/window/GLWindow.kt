package org.openartifact.artifact.core.graphics.window

import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.system.MemoryUtil.NULL
import org.openartifact.artifact.core.GameContext
import org.openartifact.artifact.core.event.events.KeyPressEvent
import org.openartifact.artifact.core.event.events.KeyReleaseEvent
import org.openartifact.artifact.core.event.events.KeyRepeatEvent
import org.openartifact.artifact.core.event.notify


class GLWindow(override val profile : WindowProfile) : Window(profile) {

    private var window : Long = 0

    override fun initAPI() {
        GLFWErrorCallback.createPrint(System.err).set()

        require(glfwInit()) { "Failed to initialize GLFW" }

        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
        glfwWindowHint(GLFW_RESIZABLE, if (profile.resizable) GLFW_TRUE else GLFW_FALSE)

        window = glfwCreateWindow(profile.width, profile.height, "Artifact <${profile.title}> OpenGL", NULL, NULL)

        profile.windowId = window

        require(window != NULL) { "Failed to create GLFW window" }

        glfwSetKeyCallback(
            window
        ) { window : Long, key : Int, scancode : Int, action : Int, mods : Int ->
            when (action) {
                GLFW_PRESS -> notify(KeyPressEvent(key))
                GLFW_RELEASE -> notify(KeyReleaseEvent(key))
                GLFW_REPEAT -> notify(KeyRepeatEvent(key))
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
        glfwSwapInterval(if (profile.targetFPS > 0) 0 else 1)
        glfwShowWindow(window)
    }

    override fun render() {
        GL.createCapabilities()

        glfwSetFramebufferSizeCallback(window) { _, width, height ->
            glViewport(0, 0, width, height)
        }

        glClearColor(0.0f, 0.0f, 0.2f, 1.0f)

        while (! glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT)

            GameContext.current().update()

            glfwSwapBuffers(window)

            glfwPollEvents()
        }
    }

    override fun initWindow() {
        initAPI()
        render()

        GameContext.current().rest()

        glfwFreeCallbacks(window)
        glfwDestroyWindow(window)

        glfwTerminate()
        glfwSetErrorCallback(null)?.free()
    }

}