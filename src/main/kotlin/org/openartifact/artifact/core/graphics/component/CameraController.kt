package org.openartifact.artifact.core.graphics.component

import glm_.vec3.Vec3
import org.lwjgl.glfw.GLFW
import org.openartifact.artifact.core.event.events.KeyPressEvent
import org.openartifact.artifact.core.event.events.KeyRepeatEvent
import org.openartifact.artifact.core.event.handler
import org.openartifact.artifact.game.Component
import org.openartifact.artifact.game.nodes.CameraNode

class CameraController : Component() {

    @Suppress("unused")
    val keyboardHandler = handler<KeyRepeatEvent>({ event ->
        val camera = parent as CameraNode

        val movement = .1

        if (event.key == GLFW.GLFW_KEY_W) camera.updatePosition(Vec3(0, 0, -movement))
        if (event.key == GLFW.GLFW_KEY_A) camera.updatePosition(Vec3(-movement, 0, 0))
        if (event.key == GLFW.GLFW_KEY_S) camera.updatePosition(Vec3(0, 0, movement))
        if (event.key == GLFW.GLFW_KEY_D) camera.updatePosition(Vec3(movement, 0, 0))
    })

}