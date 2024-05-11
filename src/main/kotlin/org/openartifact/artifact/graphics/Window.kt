package org.openartifact.artifact.graphics

import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil.NULL
import org.openartifact.artifact.core.Artifact
import org.openartifact.artifact.graphics.interfaces.IContext
import org.openartifact.artifact.graphics.platform.opengl.context.OpenGLContext
import org.openartifact.artifact.graphics.platform.opengl.context.OpenGLContextOptions
import org.openartifact.artifact.timeInit
import org.slf4j.LoggerFactory

class Window {

    var handle : Long = 0
    private lateinit var context : IContext

    private val logger = LoggerFactory.getLogger(javaClass)

    fun run() {
        logger.info("Initializing window...")
        init()
        Artifact.instance.application.init()

        logger.info("Engine startup took: ~${(System.currentTimeMillis() - timeInit)}ms")

        update()
        Artifact.instance.application.shutdown()
        shutdown()
    }

    private fun init() {
        GLFWErrorCallback.createPrint(System.err).set()

        require(glfwInit()) { "Failed to initialize GLFW" }

        // Temporarily set the context to be OpenGL.
        context = when (Artifact.instance.application.api) {
            RenderAPI.OpenGL -> OpenGLContext(this, OpenGLContextOptions(true))
            RenderAPI.Vulkan -> TODO()
        }

        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE)

        context.preInit()

        handle = glfwCreateWindow(300, 300, "Hello World", NULL, NULL)

        if (handle == NULL) throw RuntimeException("Failed to create the GLFW window")

        context.init()

        // Center the window on the screen
        MemoryStack.stackPush().use { stack ->
            val pWidth = stack.mallocInt(1)
            val pHeight = stack.mallocInt(1)

            glfwGetWindowSize(handle, pWidth, pHeight)

            val vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor())!!

            glfwSetWindowPos(
                handle,
                (vidmode.width() - pWidth.get(0)) / 2,
                (vidmode.height() - pHeight.get(0)) / 2
            )
        }

        glfwSwapInterval(1)
        glfwShowWindow(handle)
    }

    private fun update() {
        while (!glfwWindowShouldClose(handle)) {
            context.swapBuffers()
            glfwPollEvents()
        }
    }

    private fun shutdown() {
        glfwFreeCallbacks(handle)
        glfwDestroyWindow(handle)

        glfwTerminate()
        glfwSetErrorCallback(null)?.free()
    }

}