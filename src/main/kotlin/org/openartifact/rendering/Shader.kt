package org.openartifact.gamecore

import org.lwjgl.opengl.GL20.*
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

/**
 * A class for managing and rendering Shaders written in GLSL
 */
class Shader(vertexShaderFile: File, fragmentShaderFile: File) {

    private var programID: Int = 0

    init {
        // Load and compile vertex shader
        val vertexShaderID = glCreateShader(GL_VERTEX_SHADER)
        val vertexShaderCode = vertexShaderFile.readText()
        glShaderSource(vertexShaderID, vertexShaderCode)
        glCompileShader(vertexShaderID)
        checkShaderCompilation(vertexShaderID, "Vertex Shader")

        // Load and compile fragment shader
        val fragmentShaderID = glCreateShader(GL_FRAGMENT_SHADER)
        val fragmentShaderCode = fragmentShaderFile.readText()
        glShaderSource(fragmentShaderID, fragmentShaderCode)
        glCompileShader(fragmentShaderID)
        checkShaderCompilation(fragmentShaderID, "Fragment Shader")

        // Link the program
        programID = glCreateProgram()
        glAttachShader(programID, vertexShaderID)
        glAttachShader(programID, fragmentShaderID)
        glLinkProgram(programID)
        checkProgramLinking(programID)

        // Clean up shaders as they are now linked into the program
        glDetachShader(programID, vertexShaderID)
        glDetachShader(programID, fragmentShaderID)
        glDeleteShader(vertexShaderID)
        glDeleteShader(fragmentShaderID)
    }

    private fun checkShaderCompilation(shaderID: Int, shaderType: String) {
        val success = glGetShaderi(shaderID, GL_COMPILE_STATUS)
        if (success == GL_FALSE) {
            val infoLog = glGetShaderInfoLog(shaderID)
            System.err.println("ERROR::SHADER_COMPILATION_ERROR of type: $shaderType\n$infoLog\n -- --------------------------------------------------- -- ")
        }
    }

    private fun checkProgramLinking(programID: Int) {
        val success = glGetProgrami(programID, GL_LINK_STATUS)
        if (success == GL_FALSE) {
            val infoLog = glGetProgramInfoLog(programID)
            System.err.println("ERROR::PROGRAM_LINKING_ERROR\n$infoLog\n -- --------------------------------------------------- -- ")
        }
    }

    fun use() {
        glUseProgram(programID)
    }

    fun cleanup() {
        if (programID != 0) {
            glDeleteProgram(programID)
        }
    }
}
