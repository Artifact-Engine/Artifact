package org.openartifact.artifact.graphics.cameras

import glm_.func.toRadians
import glm_.glm
import glm_.mat4x4.Mat4
import glm_.vec3.Vec3
import org.openartifact.artifact.core.Artifact
import org.openartifact.artifact.core.Node
import org.openartifact.artifact.graphics.Camera

class PerspectiveCamera(private val fov : Float, position : Vec3, rotation : Vec3) : Camera, Node(position, rotation, Vec3()) {

    override fun calculateViewMatrix() : Mat4 {
        val viewMatrix = Mat4()
        viewMatrix.identity()

        viewMatrix.rotateAssign(
            toRadians(rotation.x), Vec3(1, 0, 0)
        )
            .rotateAssign(
                toRadians(rotation.y), Vec3(0, 1, 0)
            )

        viewMatrix.translateAssign(-position.x, -position.y, -position.z)

        return viewMatrix
    }

    override fun calculateProjectionMatrix() : Mat4 =
        glm.perspective(
            glm.radians(fov),
            Artifact.instance.application.windowConfig.width /
                    Artifact.instance.application.windowConfig.height,
            0.1f,
            100f
        )
}