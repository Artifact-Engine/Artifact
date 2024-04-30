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
import org.openartifact.artifact.core.Context
import org.openartifact.artifact.core.EngineState
import org.openartifact.artifact.core.event.events.*
import org.openartifact.artifact.core.event.notify
import org.slf4j.LoggerFactory

internal class Window(val profile: WindowProfile) {

    private val logger = LoggerFactory.getLogger(javaClass)
    internal var window: Long = 0

    private var lastFrameTime: Double = 0.0
    private var updatesPerSecondCounter = 0
    private var lastUpsUpdateTime = glfwGetTime()

    private var frameCount = 0
    private var lastFPSUpdateTime = glfwGetTime()

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

        logger.debug("Adjusting window position...")
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

        glfwMakeContextCurrent(window)

        logger.debug("Setting window icon...")
        setWindowIcon()

        glfwSwapInterval(if (profile.targetFPS > 0) 1 else 0)
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

    private fun update(deltaTime: Double) {
        Context.current().update(deltaTime)
        updatesPerSecondCounter++
    }

    private fun render(deltaTime: Double) {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)
        Context.current().render(deltaTime)
        glfwSwapBuffers(window)
        glfwPollEvents()
    }

    private fun updateWindowTitle() {
        glfwSetWindowTitle(window, "Artifact <" + profile.title + "> OpenGL | FPS: ${Context.current().currentFPS} UPS: $updatesPerSecondCounter")
    }

    private fun render() {
        GL.createCapabilities()

        glfwSetFramebufferSizeCallback(window) { _, width, height ->
            GL11.glViewport(0, 0, width, height)
        }

        GL11.glClearColor(0.0f, 0.0f, 0.2f, 1.0f)

        Context.current().load()

        val updateInterval = 1.0 / profile.targetUPS // Fixed update interval based on target UPS
        var lastUpdateTime = glfwGetTime()
        var previousTime = glfwGetTime()
        var accumulator = 0.0

        while (Context.current().engine.engineState === EngineState.Running && !glfwWindowShouldClose(window)) {
            val currentTime = glfwGetTime()
            val deltaTime = currentTime - lastFrameTime
            lastFrameTime = currentTime
            frameCount++

            // Accumulate time
            accumulator += deltaTime

            // Perform updates until the accumulator is large enough
            while (accumulator >= updateInterval) {
                update(updateInterval)
                accumulator -= updateInterval
            }

            // Render as fast as possible
            render(deltaTime)

            // Update window title with FPS and UPS
            if (currentTime - lastUpsUpdateTime >= 1.0) {
                Context.current().currentUPS = updatesPerSecondCounter
                updateWindowTitle()
                updatesPerSecondCounter = 0
                lastUpsUpdateTime = currentTime
            }

            if (currentTime - previousTime >= 1.0) {
                // Calculate FPS
                val fps = frameCount.toDouble() / (currentTime - previousTime)
                // Display the FPS here any way you want.
                Context.current().currentFPS = fps.toInt()

                frameCount = 0
                previousTime = currentTime
            }

            // Sleep to reduce CPU usage if the FPS is too high
            if (profile.targetFPS > 0) {
                val frameTime = 1.0 / profile.targetFPS
                val endTime = lastFrameTime + frameTime
                while (glfwGetTime() < endTime) {
                    // Busy wait until the next frame should be rendered
                }
            }
        }
    }



    private fun terminate() {
        Context.current().rest()

        Callbacks.glfwFreeCallbacks(window)
        glfwDestroyWindow(window)

        glfwTerminate()
        glfwSetErrorCallback(null)!!.free()
    }

    fun initWindow() {
        initAPI()
        render()
        terminate()
    }
}
