/*
 * Copyright Artifact-Engine (c) 2024.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package org.openartifact.artifact.utils

import glm_.func.toRadians
import glm_.mat4x4.Mat4
import glm_.vec3.Vec3

fun calculateModelMatrix(pos : Vec3, rotation : Vec3) : Mat4 =
    Mat4().identity().translateAssign(pos)
        .rotateXassign(toRadians(-rotation.x))
        .rotateYassign(toRadians(-rotation.y))
        .rotateZassign(toRadians(-rotation.z))
