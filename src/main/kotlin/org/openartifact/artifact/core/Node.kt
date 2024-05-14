/*
 * Copyright Artifact-Engine (c) 2024.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package org.openartifact.artifact.core

import glm_.func.toRadians
import glm_.vec3.Vec3
import glm_.vec4.Vec4
import kotlin.math.cos
import kotlin.math.sin

open class Node(val position : Vec3, val rotation : Vec3, val scale : Vec3) {

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

    fun updateScale(vec3 : Vec3) {
        scale.x = vec3.x
        scale.y = vec3.y
        scale.z = vec3.z
    }

    fun updateScale(x : Float, y : Float, z : Float) {
        scale.x = x
        scale.y = y
        scale.z = z
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

    fun move(movement : Vec3) =
        move(movement.x, movement.y, movement.z)

    fun move(movement : Vec3, speed : Float) =
        move(movement.x, movement.y, movement.z, speed)

    fun move(movement : Vec4) =
        move(movement.x, movement.y, movement.z, movement.w)

}