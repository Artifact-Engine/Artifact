package org.openartifact.node.dynamic

import glm_.vec3.Vec3
import org.openartifact.node.Node
import org.openartifact.scripting.Identifier

@Identifier("translation")
open class Translation(val worldCoordinate: Vec3): Node()