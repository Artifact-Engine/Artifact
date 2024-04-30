package org.openartifact.artifact.core.graphics.component

import glm_.vec3.Vec3
import org.openartifact.artifact.core.*
import org.openartifact.artifact.game.Component
import org.openartifact.artifact.game.nodes.CameraNode

class CameraController : Component() {

    override fun update(physicsDeltaTime: Double) {

        val camera = parent as CameraNode
        val speed = 4 * physicsDeltaTime

        onKey(KEY_W) {
            camera.updatePosition(Vec3(0, 0, -speed))
        }

        onKey(KEY_A) {
            camera.updatePosition(Vec3(-speed, 0, 0))
        }

        onKey(KEY_S) {
            camera.updatePosition(Vec3(0, 0, speed))
        }

        onKey(KEY_D) {
            camera.updatePosition(Vec3(speed, 0, 0))
        }

        onKey(KEY_E) {
            camera.updatePosition(Vec3(0, speed, 0))
        }

        onKey(KEY_Q) {
            camera.updatePosition(Vec3(0, -speed, 0))
        }
    }

}