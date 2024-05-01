package org.openartifact.artifact.utils

fun IntArray.toFloatArray(): FloatArray {
    return this.map { it.toFloat() }.toFloatArray()
}