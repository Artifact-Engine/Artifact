package org.openartifact.artifact.game.nodes

import glm_.vec3.Vec3
import org.openartifact.artifact.core.graphics.component.CubeRenderer
import org.openartifact.artifact.game.Component
import org.openartifact.artifact.game.Node

class CubeNode(position : Vec3, rotation : Vec3, scale : Vec3) : StaticBodyNode(position, rotation, scale) {

    override fun requiredComponents(): MutableList<Component> {
        return mutableListOf(
            CubeRenderer()
        )
    }


}