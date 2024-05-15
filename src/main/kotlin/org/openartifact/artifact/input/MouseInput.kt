package org.openartifact.artifact.input

import glm_.vec2.Vec2
import org.lwjgl.glfw.GLFW.*

object MouseInput {

    private var sensitivity: Float = 1.0f
    private val previousPos: Vec2 = Vec2(-1f, -1f)
    private val currentPos: Vec2 = Vec2(0f, 0f)
    private val displayVec: Vec2 = Vec2()

    private val pressed = mutableSetOf<Int>()
    private var inWindow = true

    fun initialize(windowId: Long) {
        // Cursor position callback
        glfwSetCursorPosCallback(windowId) { _, xPos, yPos ->
            currentPos.x = xPos.toFloat()
            currentPos.y = yPos.toFloat()

            if (previousPos.x >= 0 && previousPos.y >= 0 && inWindow) {
                val deltaX = currentPos.x - previousPos.x
                val deltaY = currentPos.y - previousPos.y

                if (deltaX != 0f || deltaY != 0f) {
                    displayVec.x = deltaX * sensitivity
                    displayVec.y = deltaY * sensitivity
                    // Call the move handler
                    moveHandler(displayVec)
                }
            }

            previousPos.x = currentPos.x
            previousPos.y = currentPos.y
        }

        // Cursor enter callback
        glfwSetCursorEnterCallback(windowId) { _, entered ->
            inWindow = entered
        }

        // Mouse button callback
        glfwSetMouseButtonCallback(windowId) { _, button, action, _ ->
            if (action == GLFW_PRESS) {
                pressed.add(button)
            } else if (action == GLFW_RELEASE) {
                pressed.remove(button)
            }
        }
    }

    // Function to handle mouse movement
    private lateinit var moveHandler: (Vec2) -> Unit
    fun move(handler: (Vec2) -> Unit) {
        moveHandler = handler
    }

    // Function to handle mouse button hold
    fun hold(button: Int, handler: () -> Unit) {
        if (pressed.contains(button)) {
            handler()
        }
    }

    // Factory function to create MouseInput instance with sensitivity
    fun process(sensitivity: Float = 0.5f, handler : () -> Unit): MouseInput {
        this.sensitivity = sensitivity
        handler()
        return this
    }
}