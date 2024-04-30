package org.openartifact.artifact.core.graphics.component

import glm_.vec3.Vec3
import org.openartifact.artifact.core.input.*
import org.openartifact.artifact.game.Component
import org.openartifact.artifact.game.nodes.CameraNode

class CameraController : Component() {

    override fun update(physicsDeltaTime: Double) {
        val camera = parent as CameraNode

        val speed : Float = if (getKeyDown(KEY_LEFT_SHIFT)) 8 * physicsDeltaTime.toFloat() else 2 * physicsDeltaTime.toFloat()

        keyMap {
            KEY_W combineWith KEY_Q combineWith KEY_E to { println("Hallo") }
        }.process()

        keyMap {
            KEY_W to { camera.movePosition(0f, 0f, -speed) }
            KEY_A to { camera.movePosition(-speed, 0f, 0f) }
            KEY_S to { camera.movePosition(0f, 0f, speed) }
            KEY_D to { camera.movePosition(speed, 0f, 0f) }

            KEY_E to { camera.movePosition(0f, speed, 0f) }
            KEY_Q to { camera.movePosition(0f, -speed, 0f) }
        }.process()
    }
}
