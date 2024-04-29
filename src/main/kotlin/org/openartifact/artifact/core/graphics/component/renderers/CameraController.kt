package org.openartifact.artifact.core.graphics.component.renderers

import glm_.vec3.Vec3
import org.lwjgl.glfw.GLFW
import org.openartifact.artifact.core.event.events.KeyPressEvent
import org.openartifact.artifact.core.event.handler
import org.openartifact.artifact.game.Component
import org.openartifact.artifact.game.nodes.CameraNode

class CameraController : Component() {

    val keyboardHandler = handler<KeyPressEvent>({ event ->
        val camera = parent as CameraNode

        if (event.key == GLFW.GLFW_KEY_W) camera.updatePosition(Vec3(0, 0, -1))
        if (event.key == GLFW.GLFW_KEY_A) camera.updatePosition(Vec3(-1, 0, 0))
        if (event.key == GLFW.GLFW_KEY_S) camera.updatePosition(Vec3(0, 0, 1))
        if (event.key == GLFW.GLFW_KEY_D) camera.updatePosition(Vec3(1, 0, 0))
    })

}