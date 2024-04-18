package org.openartifact.node.fixed.cube

import glm_.vec3.Vec3
import org.openartifact.node.NodeRenderer
import org.openartifact.node.dynamic.Translation
import org.openartifact.scripting.Identifier

@Identifier("cube")
class CubeNode(worldCoordinate: Vec3): Translation(worldCoordinate) {

    @Transient override var renderer: NodeRenderer<*>? = CubeRenderer()

}
