package org.openartifact.artifact.utils

import glm_.vec1.Vec1
import glm_.vec3.Vec3

fun IntArray.toFloatArray(): FloatArray {
    return this.map { it.toFloat() }.toFloatArray()
}

fun Vec3.plus() : Vec3 {
    this.y++
    this.x++
    this.z++
    return this
}