/*
 * Copyright Artifact-Engine (c) 2024.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package org.openartifact.artifact.extensions

import glm_.vec2.Vec2
import glm_.vec3.Vec3
import glm_.vec4.Vec4

fun Vec3.reset() {
    x = 0f
    y = 0f
    z = 0f
}

fun Vec2.reset() {
    x = 0f
    y = 0f
}

fun Vec4.reset() {
    x = 0f
    y = 0f
    z = 0f
    w = 0f
}
