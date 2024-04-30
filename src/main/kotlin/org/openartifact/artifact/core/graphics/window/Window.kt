package org.openartifact.artifact.core.graphics.window

import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.glfw.GLFWImage
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.stb.STBImage
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil
import org.openartifact.artifact.core.EngineState
import org.openartifact.artifact.core.Context
import org.openartifact.artifact.core.event.events.KeyPressEvent
import org.openartifact.artifact.core.event.events.KeyReleaseEvent
import org.openartifact.artifact.core.event.events.KeyRepeatEvent
import org.openartifact.artifact.core.event.notify
import java.nio.ByteBuffer

internal class Window(val profile : WindowProfile) {

    private var window : Long = 0

    private fun initAPI() {
        GLFWErrorCallback.createPrint(System.err).set()

        check(GLFW.glfwInit()) { "Failed to initialize GLFW" }

        GLFW.glfwDefaultWindowHints()
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE)
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, if (profile.resizable) GLFW.GLFW_TRUE else GLFW.GLFW_FALSE)

        window = GLFW.glfwCreateWindow(
            profile.width,
            profile.height,
            "Artifact <" + profile.title + "> OpenGL",
            MemoryUtil.NULL,
            MemoryUtil.NULL
        )

        if (window == MemoryUtil.NULL) {
            throw RuntimeException("Failed to create the GLFW window")
        }

        profile.windowId = window

        GLFW.glfwSetKeyCallback(
            window
        ) { _: Long, key : Int, _: Int, action : Int, _: Int ->
            when (action) {
                GLFW.GLFW_PRESS -> notify(KeyPressEvent(key))
                GLFW.GLFW_RELEASE -> notify(KeyReleaseEvent(key))
                GLFW.GLFW_REPEAT -> notify(KeyRepeatEvent(key))
            }
        }

        MemoryStack.stackPush().use { stack ->
            val pWidth = stack.mallocInt(1)
            val pHeight = stack.mallocInt(1)

            GLFW.glfwGetWindowSize(window, pWidth, pHeight)

            val vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor())
            GLFW.glfwSetWindowPos(
                window,
                (vidmode !!.width() - pWidth[0]) / 2,
                (vidmode.height() - pHeight[0]) / 2
            )
        }

        GLFW.glfwMakeContextCurrent(window)

        //setWindowIcon()

        GLFW.glfwSwapInterval(if (profile.targetFPS > 0) 0 else 1)
        GLFW.glfwShowWindow(window)
    }

    private fun setWindowIcon() {
        val iconPath = profile.iconFile.absolutePath
        val iconWidth = 128
        val iconHeight = 128

        MemoryStack.stackPush().use { stack ->
            val w = stack.mallocInt(1)
            val h = stack.mallocInt(1)
            val comp = stack.mallocInt(1)

            val iconBuffer = ByteBuffer.allocateDirect(iconWidth * iconHeight * 4)
            STBImage.stbi_load(iconPath, w, h, comp, 4)

            val icons = GLFWImage.malloc(1)
            icons.width(w.get(0))
            icons.height(h.get(0))
            icons.pixels(iconBuffer)

            GLFW.glfwSetWindowIcon(window, icons)

            STBImage.stbi_image_free(iconBuffer)
            icons.free()
        }
    }

    private fun render() {
        GL.createCapabilities()

        GLFW.glfwSetFramebufferSizeCallback(
            window
        ) { window : Long, width : Int, height : Int ->
            GL11.glViewport(
                0,
                0,
                width,
                height
            )
        }

        GL11.glClearColor(0.0f, 0.0f, 0.2f, 1.0f)

        Context.current().load()

        while (Context.current().engine.engineState === EngineState.Running && ! GLFW.glfwWindowShouldClose(window)) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)

            Context.current().update()

            GLFW.glfwSwapBuffers(window)

            GLFW.glfwPollEvents()
        }
    }

    private fun terminate() {
        Context.current().rest()

        Callbacks.glfwFreeCallbacks(window)
        GLFW.glfwDestroyWindow(window)

        GLFW.glfwTerminate()
        GLFW.glfwSetErrorCallback(null) !!.free()
    }

    fun initWindow() {
        initAPI()
        render()
        terminate()
    }

}