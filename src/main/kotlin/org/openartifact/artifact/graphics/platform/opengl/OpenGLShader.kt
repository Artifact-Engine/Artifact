package org.openartifact.artifact.graphics.platform.opengl

import org.lwjgl.opengl.GL46.*
import org.openartifact.artifact.graphics.interfaces.IShader
import org.slf4j.LoggerFactory

/**
 * Utility class for compiling and using OpenGL shaders.
 * @see ShaderModule
 */
class OpenGLShader(private val shaderModuleList : List<ShaderModule>) : IShader {

    constructor(vertexSource: String, fragmentSource: String) : this(listOf(
        ShaderModule(vertexSource, GL_VERTEX_SHADER),
        ShaderModule(fragmentSource, GL_FRAGMENT_SHADER)
    ))

    private val logger = LoggerFactory.getLogger(javaClass)

    var program : Int = 0

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

        glDeleteShader(shader)
    }

    override fun create() : IShader {
        program = glCreateProgram()

        shaderModuleList.forEach(::compile)

        return this
    }

    override fun bind() {
        glUseProgram(program)
    }

    override fun unbind() {
        glUseProgram(0)
    }

    class ShaderModule(val source : String, val shaderType : Int)

}