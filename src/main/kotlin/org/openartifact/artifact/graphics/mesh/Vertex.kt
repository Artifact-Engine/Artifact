package org.openartifact.artifact.graphics.mesh

import glm_.vec2.Vec2
import glm_.vec2.Vec2i
import glm_.vec3.Vec3

data class Vertex(var position : Vec3 = Vec3(), var normal : Vec3 = Vec3(), var texCoord : Vec2 = Vec2()) {

    fun positionData() : Array<Float> =
        floatArrayOf(position.x, position.y, position.z).toTypedArray()

    fun normalData() : Array<Float> =
        floatArrayOf(normal.x, normal.y, normal.z).toTypedArray()

    fun texCoordData() : Array<Float> =
        floatArrayOf(texCoord.x, texCoord.y).toTypedArray()

    fun positionNormalData() : Array<Float> =
        positionData() + normalData()

    fun positionNormalTexCoordData() : Array<Float> =
        positionData() + normalData() + texCoordData()
}