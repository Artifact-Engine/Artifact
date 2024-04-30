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
        previousPos = Vec2(-1f, -1f)
        currentPos = Vec2(0f, 0f)
        displayVec = Vec2(0f)
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
            val deltaX : Float = (currentPos.x - previousPos.x)
            val deltaY : Float = (currentPos.y - previousPos.y)

            val rotateX = abs(deltaX) > 0.0001
            val rotateY = abs(deltaY) > 0.0001

            if (rotateX) {
                displayVec.y = deltaX
            }
            if (rotateY) {
                displayVec.x = deltaY
            }
        }

        previousPos.x = currentPos.x
        previousPos.y = currentPos.y
    }

}