/*
 * Copyright Artifact-Engine (c) 2024.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package org.openartifact.artifact.graphics.platform.opengl

import glm_.mat2x2.Mat2
import glm_.mat3x3.Mat3
import glm_.mat4x4.Mat4
import glm_.vec2.Vec2
import glm_.vec3.Vec3
import glm_.vec4.Vec4
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL46.*
import org.openartifact.artifact.graphics.interfaces.IShader
import org.slf4j.LoggerFactory

/**
 * Utility class for compiling and using OpenGL shaders.
 * @see ShaderModule
 */
class OpenGLShader : IShader {

    private var shaderModuleList : List<ShaderModule> = listOf()

    override fun create(sources : List<ShaderModule>) : IShader {
        this.shaderModuleList = sources

        program = glCreateProgram()

        shaderModuleList.forEach(::compile)

        return this
    }

    private val logger = LoggerFactory.getLogger(javaClass)

    var program : Int = 0

    private fun compile(shaderModule : ShaderModule) {
        val shader = glCreateShader(shaderModule.shaderType.id)
        glShaderSource(shader, shaderModule.source)
        glCompileShader(shader)

        if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
            val infoLog = glGetShaderInfoLog(shader)
            logger.error("Failed to compile shader: $infoLog")

            glDeleteShader(shader)
            return
        }

        link(shader)
    }

    private fun link(shader: Int) {
        glAttachShader(program, shader)
        glLinkProgram(program)

        if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
            val infoLog = glGetProgramInfoLog(program)
            logger.error("Failed to link program: $infoLog")

            glDeleteProgram(program)
            glDeleteShader(shader)

            return
        }

        glDeleteShader(shader)
    }

    private fun getLocation(name : String) =
        glGetUniformLocation(program, name)

    override fun bind() {
        glUseProgram(program)
    }

    override fun unbind() {
        glUseProgram(0)
    }

    override fun push() {
        bind()
    }

    override fun parameterInt(name : String, value : Int) =
        glUniform1i(getLocation(name), value)

    override fun parameterFloat(name : String, value : Float) =
        glUniform1f(getLocation(name), value)

    override fun parameterVec2(name : String, value : Vec2) =
        glUniform2f(getLocation(name), value.x, value.y)

    override fun parameterVec3(name : String, value : Vec3) =
        glUniform3f(getLocation(name), value.x, value.y, value.z)

    override fun parameterVec4(name : String, value : Vec4) =
        glUniform4f(getLocation(name), value.x, value.y, value.z, value.w)

    override fun parameterMat2(name : String, value : Mat2) =
        glUniformMatrix2fv(getLocation(name), false, value.array)

    override fun parameterMat3(name : String, value : Mat3) =
        glUniformMatrix3fv(getLocation(name), false, value.array)

    override fun parameterMat4(name : String, value : Mat4) =
        glUniformMatrix4fv(getLocation(name), false, value.array)

    class ShaderModule(val source : String, val shaderType : ShaderType)

}

enum class ShaderType(val id : Int) {
    FRAGMENT(GL_FRAGMENT_SHADER),
    VERTEX(GL_VERTEX_SHADER)
}