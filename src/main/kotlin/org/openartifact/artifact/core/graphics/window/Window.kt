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
import org.openartifact.artifact.core.GameContext
import org.openartifact.artifact.core.EngineState
import org.slf4j.LoggerFactory

internal class Window(val profile: WindowProfile) {

    private val logger = LoggerFactory.getLogger(javaClass)
    internal var id: Long = 0

    private lateinit var performanceMonitor : PerformanceMonitor

    private var lastFrameTime: Double = 0.0

    private fun initAPI() {
        GLFWErrorCallback.createPrint(System.err).set()

        logger.info("Initializing ..")
        check(glfwInit()) { "Failed to initialize GLFW" }

        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
        glfwWindowHint(GLFW_RESIZABLE, if (profile.resizable) GLFW_TRUE else GLFW_FALSE)

        logger.info("Creating Window...")
        id = glfwCreateWindow(
            profile.width,
            profile.height,
            "Artifact <" + profile.title + "> OpenGL",
            MemoryUtil.NULL,
            MemoryUtil.NULL
        )

        if (id == MemoryUtil.NULL) {
            throw RuntimeException("Failed to create the GLFW window")
        }

        profile.windowId = id

        logger.debug("Adjusting window position...")
        MemoryStack.stackPush().use { stack ->
            val pWidth = stack.mallocInt(1)
            val pHeight = stack.mallocInt(1)

            glfwGetWindowSize(id, pWidth, pHeight)

            val vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor())
            glfwSetWindowPos(
                id,
                (vidmode!!.width() - pWidth[0]) / 2,
                (vidmode.height() - pHeight[0]) / 2
            )
        }

        glfwMakeContextCurrent(id)

        logger.debug("Setting window icon...")
        setWindowIcon()

        logger.debug("Starting performance-monitor")
        performanceMonitor = PerformanceMonitor()

        if (profile.targetFPS != 0 && profile.targetUPS == 0) {
            val refreshRate = glfwGetVideoMode(glfwGetPrimaryMonitor())!!.refreshRate()
            profile.targetUPS = refreshRate
        }

        if (profile.targetFPS == 0 && profile.targetUPS == 0) {
            val refreshRate = glfwGetVideoMode(glfwGetPrimaryMonitor())!!.refreshRate()
            profile.targetUPS = refreshRate
        }

        val swapInterval = when {
            profile.targetFPS == 0 -> 1 // Use VSync when targetFPS is 0
            profile.targetUPS > 0 -> 0 // Disable VSync when targetUPS is specified
            else -> 1 // Default to VSync when targetFPS is not specified
        }

        glfwSwapInterval(swapInterval)
        glfwShowWindow(id)
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

            glfwSetWindowIcon(id, icons)

            STBImage.stbi_image_free(iconBuffer)
            icons.free()
        }
    }

    private fun update(deltaTime: Double) {
        GameContext.current().update(deltaTime)
    }

    private fun render(deltaTime: Double) {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)

        GameContext.current().render(deltaTime)

        glfwSwapBuffers(id)
        glfwPollEvents()
    }

    private fun loop() {
        GL.createCapabilities()

        glfwSetFramebufferSizeCallback(id) { _, width, height ->
            GL11.glViewport(0, 0, width, height)
        }

        GL11.glClearColor(0.0f, 0.0f, 0.2f, 1.0f)

        GameContext.current().load()

        val updateInterval = 1.0 / profile.targetUPS // Fixed update interval based on target UPS
        var accumulator = 0.0

        while (GameContext.current().engine.engineState === EngineState.Running && !glfwWindowShouldClose(id)) {
            val currentTime = glfwGetTime()
            val deltaTime = currentTime - lastFrameTime
            lastFrameTime = currentTime

            performanceMonitor.updateTimes()
            performanceMonitor.updateFrameCount()

            // Accumulate time
            accumulator += deltaTime

            while (accumulator >= updateInterval) {
                update(updateInterval)
                performanceMonitor.updateUPS()
                accumulator -= updateInterval
            }

            render(deltaTime)

            performanceMonitor.updateRender()

            // Sleep if necessary
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
        GameContext.current().rest()

        Callbacks.glfwFreeCallbacks(id)
        glfwDestroyWindow(id)

        glfwTerminate()
        glfwSetErrorCallback(null)!!.free()
    }

    fun initWindow() {
        initAPI()
        loop()
        terminate()
    }
}
