package org.openartifact.artifact.game.nodes

import glm_.func.toRadians
import glm_.vec3.Vec3
import org.openartifact.artifact.game.Node
import kotlin.math.cos
import kotlin.math.sin

open class DynamicBodyNode(val position : Vec3, val rotation : Vec3, val scale : Vec3) : Node() {

    fun updatePosition(vec3 : Vec3) {
        position.x = vec3.x
        position.y = vec3.y
        position.z = vec3.z
    }

    fun updatePosition(x : Float, y : Float, z : Float) {
        position.x = x
        position.y = y
        position.z = z
    }

    fun updateRotation(vec3 : Vec3) {
        rotation.x = vec3.x
        rotation.y = vec3.y
        rotation.z = vec3.z
    }

    fun updateRotation(x : Float, y : Float, z : Float) {
        rotation.x = x
        rotation.y = y
        rotation.z = z
    }

    fun rotate(offsetX : Float, offsetY : Float, offsetZ : Float) {
        rotation.x += offsetX
        rotation.y += offsetY
        rotation.z += offsetZ
    }

    fun move(offsetX : Float, offsetY : Float, offsetZ : Float) {
        if (offsetZ != 0f) {
            position.x += sin(toRadians(rotation.y)) * - 1.0f * offsetZ
            position.z += cos(toRadians(rotation.y)) * offsetZ
        }
        if (offsetX != 0f) {
            position.x += sin(toRadians((rotation.y - 90))) * - 1.0f * offsetX
            position.z += cos(toRadians((rotation.y - 90))) * offsetX
        }
        position.y += offsetY
    }

    fun move(offsetX : Float, offsetY : Float, offsetZ : Float, speed : Float) =
        move(offsetX * speed, offsetY * speed, offsetZ * speed)

}