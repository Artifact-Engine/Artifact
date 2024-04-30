package org.openartifact.artifact.core.graphics.component

import glm_.vec3.Vec3
import org.openartifact.artifact.core.*
import org.openartifact.artifact.core.input.*
import org.openartifact.artifact.game.Component
import org.openartifact.artifact.game.nodes.CameraNode

class CameraController : Component() {
    private var previousPosition = Vec3(0.0, 0.0, 0.0)
    private var currentPosition = Vec3(0.0, 0.0, 0.0)
    private var targetPosition = Vec3(0.0, 0.0, 0.0)
    private var lastUpdateTime = 0.0

    override fun update(physicsDeltaTime: Double) {
        val camera = parent as CameraNode
        val speed = 4 * physicsDeltaTime

        val map = keyMap {
            KEY_W to { camera.updatePosition(Vec3(0, 0, -speed)) }
            KEY_A to { camera.updatePosition(Vec3(-speed, 0, 0)) }
            KEY_S to { camera.updatePosition(Vec3(0, 0, speed)) }
            KEY_D to { camera.updatePosition(Vec3(speed, 0, 0)) }
            KEY_E to { camera.updatePosition(Vec3(0, speed, 0)) }
            KEY_Q to { camera.updatePosition(Vec3(0, -speed, 0)) }
            KEY_LEFT_CONTROL combineWith KEY_ESCAPE combineWith KEY_A to { println("aaa") }
        }.process()


    }
}
