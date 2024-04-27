package org.openartifact.artifact.core.graphics.node

import org.openartifact.artifact.core.graphics.Mesh
import org.openartifact.artifact.game.Node


abstract class NodeRenderer<T : Node> {

    val meshes = mutableMapOf<String, Mesh>()

    fun addMesh(meshId : String, mesh : Mesh) {
        meshes[meshId] = mesh
    }

    abstract fun initRender()
    abstract fun render()
    abstract fun cleanupRender()

}