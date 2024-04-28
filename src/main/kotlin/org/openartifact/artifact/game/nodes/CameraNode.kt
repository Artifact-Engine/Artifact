package org.openartifact.artifact.game.nodes

import glm_.mat4x4.Mat4
import glm_.vec3.Vec3
import org.openartifact.artifact.core.graphics.node.renderers.CameraController
import org.openartifact.artifact.game.Node
import org.openartifact.artifact.utils.createViewMatrix

class CameraNode(private val position : Vec3, private val target : Vec3, private val up : Vec3) : Node(CameraController()) {

    fun getViewMatrix() : Mat4 =
        createViewMatrix(position, target, up)

}