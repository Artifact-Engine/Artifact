package org.openartifact.rendering.renderers

import glm_.glm
import glm_.mat4x4.Mat4
import glm_.vec3.Vec3
import org.lwjgl.opengl.GL15.*
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL30.*
import org.openartifact.rendering.Renderer
import org.openartifact.rendering.Shader
import java.io.File

class DefaultRenderer: Renderer {

    private var vertexArrayId = 0
    private var vertexBuffer = 0
    private var colorBuffer = 0
    private var matrixId = 0
    private lateinit var shader: Shader
    private lateinit var mvp: Mat4

    override fun init() {
        glClearColor(0.0f, 0.0f, 0.4f, 0.0f);

        vertexArrayId = glGenVertexArrays()
        glBindVertexArray(vertexArrayId)

        shader = Shader(File("testfiles/testv.glsl"), File("testfiles/testf.glsl"))

        val vertexBufferData = floatArrayOf(
            -1.0f,-1.0f,-1.0f, // triangle 1 : begin
            -1.0f,-1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f, // triangle 1 : end
            1.0f, 1.0f,-1.0f, // triangle 2 : begin
            -1.0f,-1.0f,-1.0f,
            -1.0f, 1.0f,-1.0f, // triangle 2 : end
            1.0f,-1.0f, 1.0f,
            -1.0f,-1.0f,-1.0f,
            1.0f,-1.0f,-1.0f,
            1.0f, 1.0f,-1.0f,
            1.0f,-1.0f,-1.0f,
            -1.0f,-1.0f,-1.0f,
            -1.0f,-1.0f,-1.0f,
            -1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f,-1.0f,
            1.0f,-1.0f, 1.0f,
            -1.0f,-1.0f, 1.0f,
            -1.0f,-1.0f,-1.0f,
            -1.0f, 1.0f, 1.0f,
            -1.0f,-1.0f, 1.0f,
            1.0f,-1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f,-1.0f,-1.0f,
            1.0f, 1.0f,-1.0f,
            1.0f,-1.0f,-1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f,-1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f,-1.0f,
            -1.0f, 1.0f,-1.0f,
            1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f,-1.0f,
            -1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f,
            1.0f,-1.0f, 1.0f
        )

        val colorBufferData = floatArrayOf(
            0.583f,  0.771f,  0.014f,
            0.609f,  0.115f,  0.436f,
            0.327f,  0.483f,  0.844f,
            0.822f,  0.569f,  0.201f,
            0.435f,  0.602f,  0.223f,
            0.310f,  0.747f,  0.185f,
            0.597f,  0.770f,  0.761f,
            0.559f,  0.436f,  0.730f,
            0.359f,  0.583f,  0.152f,
            0.483f,  0.596f,  0.789f,
            0.559f,  0.861f,  0.639f,
            0.195f,  0.548f,  0.859f,
            0.014f,  0.184f,  0.576f,
            0.771f,  0.328f,  0.970f,
            0.406f,  0.615f,  0.116f,
            0.676f,  0.977f,  0.133f,
            0.971f,  0.572f,  0.833f,
            0.140f,  0.616f,  0.489f,
            0.997f,  0.513f,  0.064f,
            0.945f,  0.719f,  0.592f,
            0.543f,  0.021f,  0.978f,
            0.279f,  0.317f,  0.505f,
            0.167f,  0.620f,  0.077f,
            0.347f,  0.857f,  0.137f,
            0.055f,  0.953f,  0.042f,
            0.714f,  0.505f,  0.345f,
            0.783f,  0.290f,  0.734f,
            0.722f,  0.645f,  0.174f,
            0.302f,  0.455f,  0.848f,
            0.225f,  0.587f,  0.040f,
            0.517f,  0.713f,  0.338f,
            0.053f,  0.959f,  0.120f,
            0.393f,  0.621f,  0.362f,
            0.673f,  0.211f,  0.457f,
            0.820f,  0.883f,  0.371f,
            0.982f,  0.099f,  0.879f
        )

        matrixId = glGetUniformLocation(shader.programId, "MVP")

        val projectionMatrix: Mat4 = glm.perspective(glm.radians(45.0f),
            4f / 3f, 0.1f, 100.0f)

        val view: Mat4 = glm.lookAt(
            Vec3(4, 3, 3),
            Vec3(0, 0, 0),
            Vec3(0, 1, 0)
        )

        val model = Mat4(1.0f)

        mvp = projectionMatrix * view * model

        vertexBuffer = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer)
        glBufferData(GL_ARRAY_BUFFER, vertexBufferData, GL_STATIC_DRAW)

        colorBuffer = glGenBuffers()
        glBindBuffer(GL_ARRAY_BUFFER, colorBuffer)
        glBufferData(GL_ARRAY_BUFFER, colorBufferData, GL_STATIC_DRAW)
    }

    override fun render() {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT) // clear the framebuffer

        glEnable(GL_DEPTH_TEST)
        glDepthFunc(GL_LESS)

        shader.use()

        // Pass the float array to glUniformMatrix4fv
        glUniformMatrix4fv(matrixId, false, mvp.array)

        /*
        glUniform3f(GL20.glGetUniformLocation(shader.programId, "col"),
            (0..1).random().toFloat(),
            (0..1).random().toFloat(),
            (0..1).random().toFloat())
         */

        glEnableVertexAttribArray(0)
        glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer)
        glVertexAttribPointer(
            0,
            3,
            GL_FLOAT,
            false,
            0,
            0
        )

        glEnableVertexAttribArray(1)
        glBindBuffer(GL_ARRAY_BUFFER, colorBuffer)
        glVertexAttribPointer(
            1,                                // attribute. No particular reason for 1, but must match the layout in the shader.
            3,                                // size
            GL_FLOAT,                         // type
            false,                         // normalized?
            0,                                // stride
            0                          // array buffer offset
        )

        glDrawArrays(GL_TRIANGLES, 0, 12 * 3)
        glDisableVertexAttribArray(0)
        glDisableVertexAttribArray(1)
    }


    override fun shutdown() {
        shader.cleanup()
    }

}
