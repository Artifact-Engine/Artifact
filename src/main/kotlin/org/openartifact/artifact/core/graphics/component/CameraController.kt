package org.openartifact.artifact.core.graphics.component

import glm_.vec3.Vec3
import org.openartifact.artifact.core.input.*
import org.openartifact.artifact.game.Component
import org.openartifact.artifact.game.nodes.CameraNode

class CameraController : Component() {

    override fun update(physicsDeltaTime: Double) {
        val camera = parent as CameraNode

        // Adjust speed based on whether the left shift key is pressed
        val speed : Float = if (getKeyDown(KEY_LEFT_SHIFT)) 8f * physicsDeltaTime.toFloat() else 2f * physicsDeltaTime.toFloat()

        val camInc = Vec3()

        // Process key inputs to calculate movement increments
        keyMap {
            KEY_W to { camInc.z = -1f }
            KEY_A to { camInc.x = -1f }
            KEY_S to { camInc.z = 1f }
            KEY_D to { camInc.x = 1f }

            KEY_E to { camInc.y = 1f }
            KEY_Q to { camInc.y = -1f }
        }.process()

        // Apply movement increments to the camera
        camera.move(camInc.x, camInc.y, camInc.z, speed)

        // Handle mouse input for rotation
        if (mouse.rightButtonPressed)
            mouse.input((2.0f).toFloat()) { x, y ->
                camera.rotate(x, y, 0f)
            }

    }


}
