package org.openartifact.artifact.graphics.window

import org.lwjgl.glfw.Callbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil
import org.openartifact.artifact.core.Application
import org.openartifact.artifact.core.EngineState
import org.openartifact.artifact.graphics.IContext
import org.openartifact.artifact.graphics.platform.opengl.OpenGLContext
import org.slf4j.LoggerFactory

internal class Window(val profile: WindowProfile) {

    private val logger = LoggerFactory.getLogger(javaClass)
    internal var handle: Long = 0
    lateinit var context : IContext

    private lateinit var performanceMonitor : PerformanceMonitor

    private var lastFrameTime: Double = 0.0

    private fun initAPI() {
        GLFWErrorCallback.createPrint(System.err).set()

        logger.info("Initializing ..")
        check(glfwInit()) { "Failed to initialize GLFW" }

        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
        glfwWindowHint(GLFW_RESIZABLE, if (profile.resizable) GLFW_TRUE else GLFW_FALSE)

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 6)
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)

        logger.info("Creating Window...")
        handle = glfwCreateWindow(
            profile.width,
            profile.height,
            "ARTIFACT: ${profile.title}",
            MemoryUtil.NULL,
            MemoryUtil.NULL
        )

        if (handle == MemoryUtil.NULL) {
            throw RuntimeException("Failed to create the GLFW window")
        }

        logger.debug("Adjusting window position...")
        MemoryStack.stackPush().use { stack ->
            val pWidth = stack.mallocInt(1)
            val pHeight = stack.mallocInt(1)

            glfwGetWindowSize(handle, pWidth, pHeight)

            val vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor())
            glfwSetWindowPos(
                handle,
                (vidmode!!.width() - pWidth[0]) / 2,
                (vidmode.height() - pHeight[0]) / 2
            )
        }

        context = OpenGLContext(this)
        context.init()

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
        glfwShowWindow(handle)
    }

    private fun update(deltaTime: Double) {
        Application.current().update(deltaTime)
    }

    private fun render(deltaTime: Double) {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT or GL11.GL_DEPTH_BUFFER_BIT)

        Application.current().render(deltaTime)

        context.swapBuffers()
        glfwPollEvents()
    }

    private fun loop() {
        GL.createCapabilities()

        glfwSetFramebufferSizeCallback(handle) { _, width, height ->
            GL11.glViewport(0, 0, width, height)
        }

        GL11.glClearColor(0.0f, 0.0f, 0.2f, 1.0f)

        Application.current().load()

        val updateInterval = 1.0 / profile.targetUPS // Fixed update interval based on target UPS
        var accumulator = 0.0

        while (Application.current().engine.engineState === EngineState.Running && !glfwWindowShouldClose(handle)) {
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
        Application.current().rest()

        Callbacks.glfwFreeCallbacks(handle)
        glfwDestroyWindow(handle)

        glfwTerminate()
        glfwSetErrorCallback(null)!!.free()
    }

    fun initWindow() {
        initAPI()
        loop()
        terminate()
    }
}
