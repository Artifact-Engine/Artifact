package org.openartifact.artifact.core.input

import glm_.vec2.Vec2
import org.lwjgl.glfw.GLFW.*
import org.openartifact.artifact.core.GameContext
import kotlin.math.abs


class MouseInput {

    private val previousPos : Vec2
    private val currentPos : Vec2
    val displayVec : Vec2

    private var inWindow = true
    var leftButtonPressed = false
    var rightButtonPressed = false

    init {
        previousPos = Vec2(- 1, - 1)
        currentPos = Vec2(0, 0)
        displayVec = Vec2()
    }

    fun init() {
        val windowId = GameContext.current().engine.window.id

        glfwSetCursorPosCallback(windowId) { _, xPos, yPos ->
            currentPos.x = xPos.toFloat()
            currentPos.y = yPos.toFloat()
        }

        glfwSetCursorEnterCallback(windowId) { _ : Long, entered : Boolean ->
            inWindow = entered
        }

        glfwSetMouseButtonCallback(windowId) { _, button, action, mode ->
            leftButtonPressed = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS
            rightButtonPressed = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS
        }

    }

    fun input() {
        displayVec.x = 0f
        displayVec.y = 0f

        if (previousPos.x > 0 && previousPos.y > 0 && inWindow) {
            val deltaX = (currentPos.x - previousPos.x).toDouble()
            val deltaY = (currentPos.y - previousPos.y).toDouble()

            // Check if the movement is significant
            val rotateX = abs(deltaX) > 0.01
            val rotateY = abs(deltaY) > 0.01

            if (rotateX) {
                println("Rotating X: $deltaX")
                // Normalize or scale deltaX for rotation
                displayVec.y = deltaX.toFloat() // Example scaling factor
            }
            if (rotateY) {
                println("Rotating Y: $deltaY")
                // Normalize or scale deltaY for rotation
                displayVec.x = deltaY.toFloat() // Example scaling factor
            }
        }

        previousPos.x = currentPos.x
        previousPos.y = currentPos.y
    }

}