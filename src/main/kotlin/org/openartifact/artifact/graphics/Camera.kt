package org.openartifact.artifact.graphics

import glm_.mat4x4.Mat4

interface Camera {

    fun calculateViewMatrix() : Mat4

    fun calculateProjectionMatrix() : Mat4

}