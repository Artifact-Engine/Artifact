package org.openartifact.artifact.core.graphics

import glm_.mat2x2.Mat2
import glm_.mat3x3.Mat3
import glm_.mat4x4.Mat4
import glm_.vec1.Vec1
import glm_.vec2.Vec2
import glm_.vec3.Vec3
import glm_.vec4.Vec4
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL30.*

class ShaderProgram(shaderData : List<ShaderData>) {

    var programId : Int = 0

    init {
        programId = glCreateProgram()

        require(programId != 0) { "Failed to create shader program" }

        val shaderModules = mutableListOf<Int>()
        shaderData.forEach { data -> shaderModules.add(createShader(data.source, data.shaderType)) }

        link(shaderModules)
    }

    fun bind() {
        glUseProgram(programId)
    }

    fun cleanup() {
        unbind()
        if (programId == 0)
            glDeleteProgram(programId)
    }

    private fun createShader(source : String, shaderType : Int) : Int {
        val shaderId = glCreateShader(shaderType)

        require(shaderType != 0) { "Error creating shader. $shaderType" }

        glShaderSource(shaderId, source)
        glCompileShader(shaderId)

        validate()

        require(glGetShaderi(shaderId, GL_COMPILE_STATUS) != 0) { "Error compiling shader: ${glGetShaderInfoLog(shaderId)}" }

        glAttachShader(programId, shaderId)

        return shaderId
    }

    private fun link(shaderModules : List<Int>) {
        glLinkProgram(programId)

        require(glGetProgrami(programId, GL_LINK_STATUS) != 0) { "Error linking shader: ${glGetProgramInfoLog(programId)}" }

        shaderModules.forEach { shader ->
            glDetachShader(programId, shader)
            glDeleteShader(shader)
        }
    }

    internal fun unbind() {
        glUseProgram(0)
    }

    private fun validate() {
        glValidateProgram(programId)

        require(glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) { "Error validating shader: ${glGetProgramInfoLog(programId)}" }
    }

    fun applyMvpMatrix(mvpMatrix : Mat4) =
        uniformMatrix4("MVP", mvpMatrix)

    fun getUniformLocation(id : String) : Int =
        glGetUniformLocation(programId, id)

    fun uniformVec1(name : String, vec1: Vec1) =
        glUniformMatrix4fv(getUniformLocation(name), false, vec1.toFloatArray())

    fun uniformVec2(name : String, vec2: Vec2) =
        glUniformMatrix4fv(getUniformLocation(name), false, vec2.toFloatArray())

    fun uniformVec3(name : String, vec3: Vec3) =
        glUniformMatrix4fv(getUniformLocation(name), false, vec3.toFloatArray())

    fun uniformVec4(name : String, vec4: Vec4) =
        glUniformMatrix4fv(getUniformLocation(name), false, vec4.toFloatArray())

    fun uniformMatrix2(name : String, mat2: Mat2) =
        glUniformMatrix4fv(getUniformLocation(name), false, mat2.array)

    fun uniformMatrix3(name : String, mat3: Mat3) =
        glUniformMatrix4fv(getUniformLocation(name), false, mat3.array)

    fun uniformMatrix4(name : String, mat4: Mat4) =
        glUniformMatrix4fv(getUniformLocation(name), false, mat4.array)

    /**
     * @param source Code of the shader
     * @param shaderType Type of shader [GL_FRAGMENT_SHADER] / [GL_VERTEX_SHADER]
     */
    data class ShaderData(val source : String, val shaderType : Int)

}