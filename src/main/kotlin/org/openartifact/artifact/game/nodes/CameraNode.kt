package org.openartifact.artifact.game.nodes

import glm_.func.toRadians
import glm_.mat4x4.Mat4
import glm_.vec3.Vec3
import org.openartifact.artifact.core.GameContext
import org.openartifact.artifact.game.Component
import org.openartifact.artifact.game.Node
import org.openartifact.artifact.utils.createProjectionMatrix
import kotlin.math.cos
import kotlin.math.sin

class CameraNode(var fieldOfView : Float, position : Vec3, rotation : Vec3) : DynamicBodyNode(position, rotation,
    Vec3(0f)
) {

    fun getViewMatrix() : Mat4 {
        val viewMatrix = Mat4()
        viewMatrix.identity()

        viewMatrix.rotateAssign(toRadians(rotation.x), Vec3(1, 0, 0))
            .rotateAssign(toRadians(rotation.y), Vec3(0, 1, 0))

        viewMatrix.translateAssign(-position.x, -position.y, -position.z)
        return viewMatrix
    }


    fun getProjectionMatrix() : Mat4 =
        createProjectionMatrix(fieldOfView, GameContext.current().engine.window.profile.aspectRatio.ratio, 0.1f, 100.0f)

}