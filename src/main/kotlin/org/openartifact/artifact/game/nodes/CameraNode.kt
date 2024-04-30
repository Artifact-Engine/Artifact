package org.openartifact.artifact.game.nodes

import glm_.func.toRadians
import glm_.mat4x4.Mat4
import glm_.vec3.Vec3
import org.openartifact.artifact.core.GameContext
import org.openartifact.artifact.core.graphics.component.CameraController
import org.openartifact.artifact.game.Component
import org.openartifact.artifact.game.Node
import org.openartifact.artifact.utils.createProjectionMatrix
import kotlin.math.cos
import kotlin.math.sin

class CameraNode(var fov : Float, private var position : Vec3, private var rotation : Vec3) : Node() {

    fun getViewMatrix() : Mat4 {
        val viewMatrix = Mat4()
        viewMatrix.identity()

        // First do the rotation so camera rotates over its position
        viewMatrix.rotateAssign(toRadians(rotation.x), Vec3(1, 0, 0))
            .rotateAssign(toRadians(rotation.y), Vec3(0, 1, 0))

        // Then do the translation
        viewMatrix.translateAssign(-position.x, -position.y, -position.z)
        return viewMatrix
    }

    fun updatePosition(vec3 : Vec3) {
        position = vec3
    }

    fun updatePosition(x : Float, y : Float, z : Float) {
        position.x = x
        position.y = y
        position.z = z
    }

    fun updateRotation(vec3 : Vec3) {
        rotation = vec3
    }

    fun updateRotation(x : Float, y : Float, z : Float) {
        rotation.x = x
        rotation.y = y
        rotation.z = z
    }

    fun moveRotation(offsetX : Float, offsetY : Float, offsetZ : Float) {
        rotation.x += offsetX
        rotation.y += offsetY
        rotation.z += offsetZ
    }

    fun movePosition(offsetX : Float, offsetY : Float, offsetZ : Float) {
        if (offsetZ != 0f) {
            position.x += sin(toRadians(rotation.y)) * - 1.0f * offsetZ
            position.z += cos(toRadians(rotation.y)) * offsetZ
        }
        if (offsetX != 0f) {
            position.x += sin(toRadians((rotation.y - 90))) * - 1.0f * offsetX
            position.z += cos(toRadians((rotation.y - 90))) * offsetX
        }
        position.y += offsetY
    }

    fun getProjectionMatrix() : Mat4 =
        createProjectionMatrix(fov, GameContext.current().engine.window.profile.aspectRatio.ratio, 0.1f, 100.0f)

    override fun requiredComponents(): MutableList<Component> {
        return mutableListOf(CameraController())
    }

}