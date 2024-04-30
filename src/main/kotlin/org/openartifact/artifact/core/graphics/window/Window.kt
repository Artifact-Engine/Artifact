package org.openartifact.artifact.core.graphics.window

import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW.*
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
import org.slf4j.LoggerFactory

internal class Window(val profile : WindowProfile) {

    private val logger = LoggerFactory.getLogger(javaClass)
    private var window : Long = 0

    private fun initAPI() {
        GLFWErrorCallback.createPrint(System.err).set()

        logger.info("Initializing ..")
        check(glfwInit()) { "Failed to initialize GLFW" }

        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
        glfwWindowHint(GLFW_RESIZABLE, if (profile.resizable) GLFW_TRUE else GLFW_FALSE)

        logger.info("Creating Window...")
        window = glfwCreateWindow(
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

        logger.debug("Setting up key callback...")
        glfwSetKeyCallback(
            window
        ) { _: Long, key : Int, _: Int, action : Int, _: Int ->
            when (action) {
                GLFW_PRESS -> notify(KeyPressEvent(key))
                GLFW_RELEASE -> notify(KeyReleaseEvent(key))
                GLFW_REPEAT -> notify(KeyRepeatEvent(key))
            }
        }

        logger.debug("Adjusting window position...")
        MemoryStack.stackPush().use { stack ->
            val pWidth = stack.mallocInt(1)
            val pHeight = stack.mallocInt(1)

            glfwGetWindowSize(window, pWidth, pHeight)

            val vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor())
            glfwSetWindowPos(
                window,
                (vidmode !!.width() - pWidth[0]) / 2,
                (vidmode.height() - pHeight[0]) / 2
            )
        }

        glfwMakeContextCurrent(window)

        logger.debug("Setting window icon...")
        setWindowIcon()

        glfwSwapInterval(if (profile.targetFPS > 0) 0 else 1)
        glfwShowWindow(window)
    }

    private fun setWindowIcon() {
        val iconPath = profile.iconProfile.file.absolutePath
        val iconWidth = profile.iconProfile.width
        val iconHeight = profile.iconProfile.height
        println(iconPath)
        
        MemoryStack.stackPush().use { stack ->
            val w = stack.mallocInt(1)
            val h = stack.mallocInt(1)
            val comp = stack.mallocInt(1)

            val iconBuffer = MemoryUtil.memAlloc(iconWidth * iconHeight * 4)
            STBImage.stbi_load(iconPath, w, h, comp, 4)

            val icons = GLFWImage.malloc(1)
            icons.width(w.get())
            icons.height(h.get())
            icons.pixels(iconBuffer)

            glfwSetWindowIcon(window, icons)

            STBImage.stbi_image_free(iconBuffer)
            icons.free()
        }
    }

    private fun render() {
        GL.createCapabilities()

        glfwSetFramebufferSizeCallback(
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

        while (Context.current().engine.engineState === EngineState.Running && ! glfwWindowShouldClose(window)) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)

            Context.current().update()

            glfwSwapBuffers(window)

            glfwPollEvents()
        }
    }

    private fun terminate() {
        Context.current().rest()

        Callbacks.glfwFreeCallbacks(window)
        glfwDestroyWindow(window)

        glfwTerminate()
        glfwSetErrorCallback(null) !!.free()
    }

    fun initWindow() {
        initAPI()
        render()
        terminate()
    }

}