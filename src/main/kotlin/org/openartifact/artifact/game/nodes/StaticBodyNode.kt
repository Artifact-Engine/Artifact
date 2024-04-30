package org.openartifact.artifact.game.nodes

import glm_.vec3.Vec3
import org.openartifact.artifact.game.Node

open class StaticBodyNode(val position : Vec3, val rotation : Vec3, val scale : Vec3) : Node()