package org.openartifact.artifact.graphics

import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryUtil.NULL
import org.openartifact.artifact.core.Artifact
import org.openartifact.artifact.graphics.platform.opengl.OpenGLContext

class Window {

    var handle : Long = 0
    lateinit var context : IContext

    fun run() {
        init()
        update()
        shutdown()
    }

    private fun init() {
        GLFWErrorCallback.createPrint(System.err).set()

        require(glfwInit()) { "Failed to initialize GLFW" }

        context = OpenGLContext(this)

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
        while (! glfwWindowShouldClose(handle)) {
            Artifact.current().application.update()

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