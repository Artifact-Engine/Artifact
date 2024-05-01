package org.openartifact.artifact.core.graphics.component

import org.openartifact.artifact.core.graphics.Mesh
import org.openartifact.artifact.game.nodes.CubeNode

class CubeRenderer : MeshRenderer(
    Mesh(
        positions = floatArrayOf(
            // VO
            - 0.5f, 0.5f, 0.5f,
            // V1
            - 0.5f, - 0.5f, 0.5f,
            // V2
            0.5f, - 0.5f, 0.5f,
            // V3
            0.5f, 0.5f, 0.5f,
            // V4
            - 0.5f, 0.5f, - 0.5f,
            // V5
            0.5f, 0.5f, - 0.5f,
            // V6
            - 0.5f, - 0.5f, - 0.5f,
            // V7
            0.5f, - 0.5f, - 0.5f,
        ),
        colors = floatArrayOf(
            0.5f, 0.0f, 0.0f,
            0.0f, 0.5f, 0.0f,
            0.0f, 0.0f, 0.5f,
            0.0f, 0.5f, 0.5f,
            0.5f, 0.0f, 0.0f,
            0.0f, 0.5f, 0.0f,
            0.0f, 0.0f, 0.5f,
            0.0f, 0.5f, 0.5f,
        ),
        indices = intArrayOf(
            // Front face
            0, 1, 3, 3, 1, 2,
            // Top Face
            4, 0, 3, 5, 4, 3,
            // Right face
            3, 2, 7, 5, 3, 7,
            // Left face
            6, 1, 0, 6, 0, 4,
            // Bottom face
            2, 1, 6, 2, 6, 7,
            // Back face
            7, 6, 4, 7, 4, 5,
        )
    )
) {

    override fun update(physicsDeltaTime : Double) {
        super.update(physicsDeltaTime)

        val node = parent as CubeNode

        val rotation = node.rotation

        rotation.y++

        if (rotation.y >= 360f) {
            rotation.y = 0f
        }

        println(rotation)

        node.updateRotation(rotation)
    }

}