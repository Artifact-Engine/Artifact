package org.openartifact.artifact.core.graphics

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

        require(glGetProgrami(programId, GL_VALIDATE_STATUS) != 0) { "Error validating shader: ${glGetProgramInfoLog(programId)}" }
    }

    data class ShaderData(val source : String, val shaderType : Int)

}