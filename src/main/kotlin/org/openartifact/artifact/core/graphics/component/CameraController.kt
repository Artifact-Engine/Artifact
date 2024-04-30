package org.openartifact.artifact.core.graphics.component

import glm_.vec3.Vec3
import org.openartifact.artifact.core.*
import org.openartifact.artifact.core.event.events.KeyPressEvent
import org.openartifact.artifact.core.event.handler
import org.openartifact.artifact.game.Component
import org.openartifact.artifact.game.nodes.CameraNode

class CameraController : Component() {

    @Suppress("unused")
    val keyboardHandler = handler<KeyPressEvent>({ event ->
        val camera = parent as CameraNode

        val movement = 1

        camera.updatePosition(when (event.key) {
            KEY_W -> Vec3(0, 0, -movement)
            KEY_A -> Vec3(-movement, 0, 0)
            KEY_S -> Vec3(0, 0, movement)
            KEY_D -> Vec3(movement, 0, 0)
            KEY_E -> Vec3(0, movement, 0)
            KEY_Q -> Vec3(0, -movement, 0)
            else -> Vec3(0, 0,0)
        })
    })

}