package org.openartifact.artifact.core.graphics.component

import glm_.vec2.Vec2
import glm_.vec3.Vec3
import org.openartifact.artifact.core.input.*
import org.openartifact.artifact.game.Component
import org.openartifact.artifact.game.nodes.CameraNode

class CameraController : Component() {

    private lateinit var mouseInput : MouseInput

    override fun awake() {
        mouseInput = MouseInput()
        mouseInput.init()
    }

    override fun update(physicsDeltaTime: Double) {
        val camera = parent as CameraNode

        val speed : Float = if (getKeyDown(KEY_LEFT_SHIFT)) 8f * physicsDeltaTime.toFloat() else 2f * physicsDeltaTime.toFloat()

        val camInc = Vec3()

        keyMap {
            KEY_W to { camInc.z = -1f }
            KEY_A to { camInc.x = -1f }
            KEY_S to { camInc.z = 1f }
            KEY_D to { camInc.x = 1f }

            KEY_E to { camInc.y = 1f }
            KEY_Q to { camInc.y = -1f }
        }.process()

        camera.movePosition(camInc.x * speed, camInc.y * speed, camInc.z * speed)

        mouseInput.input()

        if (mouseInput.rightButtonPressed) {
            val rotVec : Vec2 = mouseInput.displayVec
            val mouseSensitivity = 30f * physicsDeltaTime.toFloat()
            camera.moveRotation(rotVec.x * mouseSensitivity, rotVec.y * mouseSensitivity, 0f)
        }
    }
}
