package org.openartifact.artifact.game.nodes

import glm_.mat4x4.Mat4
import glm_.vec3.Vec3
import org.openartifact.artifact.core.graphics.component.renderers.CameraController
import org.openartifact.artifact.game.Component
import org.openartifact.artifact.game.Node
import org.openartifact.artifact.utils.createProjectionMatrix
import org.openartifact.artifact.utils.createViewMatrix

class CameraNode(var fov : Float, var position : Vec3, private var target : Vec3, private val up : Vec3) : Node() {

    fun getViewMatrix() : Mat4 =
        createViewMatrix(position, target, up)

    fun getProjectionMatrix() : Mat4 =
        createProjectionMatrix(fov, 4.0f / 3.0f, 0.1f, 100.0f)

    fun updatePosition(new : Vec3) {
        target.plusAssign(new)
        position.plusAssign(new)
    }

    override fun requiredComponents(): MutableList<Component> {
        return mutableListOf(CameraController())
    }

}