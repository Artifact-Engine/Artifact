/*
 * Copyright Artifact-Engine (c) 2024.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package org.openartifact.artifact.graphics.interfaces

import glm_.mat2x2.Mat2
import glm_.mat3x3.Mat3
import glm_.mat4x4.Mat4
import glm_.vec2.Vec2
import glm_.vec3.Vec3
import glm_.vec4.Vec4
import org.openartifact.artifact.graphics.IGraphicsComponent

interface IShader : IGraphicsComponent {

    fun create() : IShader
    fun bind()
    fun unbind()

    fun parameterInt(name : String, value : Int)

     fun parameterFloat(name : String, value : Float)

     fun parameterVec2(name : String, value : Vec2)

     fun parameterVec3(name : String, value : Vec3)

     fun parameterVec4(name : String, value : Vec4)

     fun parameterMat2(name : String, value : Mat2)

     fun parameterMat3(name : String, value : Mat3)

     fun parameterMat4(name : String, value : Mat4)

}
