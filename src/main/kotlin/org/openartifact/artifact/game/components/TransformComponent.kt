package org.openartifact.artifact.game.components

import glm_.vec3.Vec3
import org.openartifact.artifact.game.Component

class TransformComponent (
    val position: Vec3,
    val rotation: Vec3,
    val scale: Vec3
    ) : Component()