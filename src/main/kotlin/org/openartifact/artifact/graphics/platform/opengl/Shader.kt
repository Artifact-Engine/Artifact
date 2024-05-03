package org.openartifact.artifact.graphics.platform.opengl

import org.lwjgl.opengl.GL46.*
import org.slf4j.LoggerFactory

class Shader(private val shaderModuleList : List<ShaderModule>) {

    constructor(vertexSource: String, fragmentSource: String) : this(listOf(
        ShaderModule(vertexSource, GL_VERTEX_SHADER),
        ShaderModule(fragmentSource, GL_FRAGMENT_SHADER)
    ))

    private val logger = LoggerFactory.getLogger(javaClass)

    var program : Int = 0

    init {
        program = glCreateProgram()

        shaderModuleList.forEach(::compile)
    }

    private fun compile(shaderModule : ShaderModule) {
        val shader = glCreateShader(shaderModule.shaderType)
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

        cleanup(shader)
    }

    fun bind() {
        glUseProgram(program)
    }

    private fun cleanup(shader : Int) {
        glDetachShader(program, shader)
        glDeleteShader(shader)
    }

    class ShaderModule(val source : String, var shaderType : Int = 0)

}