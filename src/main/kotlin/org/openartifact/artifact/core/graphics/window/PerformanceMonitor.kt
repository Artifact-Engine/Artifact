package org.openartifact.artifact.core.graphics.window

import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFW.glfwSetWindowTitle
import org.openartifact.artifact.core.Context
import org.openartifact.artifact.core.Engine

class PerformanceMonitor {

    var currentFPS = 0
    var currentUPS = 0

    private var frames = 0
    private var previousTime = GLFW.glfwGetTime()
    private var currentTime = GLFW.glfwGetTime()
    private var lastUpsUpdateTime = GLFW.glfwGetTime()
    private var updatesPerSecondCounter = 0

    private fun updateWindowTitle() {
        val currentFPS = currentFPS
        val currentUPS = currentUPS

        val window = Context.current().engine.window.window
        val profile = Context.current().engine.window.profile

        val newTitle = "Artifact <${profile.title}> OpenGL | FPS: $currentFPS UPS: $currentUPS"

        glfwSetWindowTitle(window, newTitle)
    }

    fun updateFrameCount() {
        frames++
    }

    fun resetFrameCount() {
        frames = 0
    }

    fun updateTimes() {
        currentTime = GLFW.glfwGetTime()
    }

    fun updateUPS() {
        updatesPerSecondCounter++
    }

    fun updateRender() {
        if (currentTime - lastUpsUpdateTime >= 1.0) {
            currentUPS = updatesPerSecondCounter
            updatesPerSecondCounter = 0
            lastUpsUpdateTime = currentTime
        }

        if (currentTime - previousTime >= 1.0) {
            val fps = frames.toDouble() / (currentTime - previousTime)

            currentFPS = fps.toInt()
            resetFrameCount()
            previousTime = currentTime

            updateWindowTitle()
        }
    }
}
