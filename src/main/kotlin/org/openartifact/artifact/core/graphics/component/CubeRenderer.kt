package org.openartifact.artifact.core.graphics.component

import glm_.mat4x4.Mat4
import glm_.vec3.Vec3
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30.*
import org.openartifact.artifact.core.graphics.Mesh
import org.openartifact.artifact.core.graphics.ShaderProgram
import org.openartifact.artifact.core.graphics.Texture
import org.openartifact.artifact.game.Component
import org.openartifact.artifact.utils.*
import java.io.File

class CubeRenderer : Component() {

    private lateinit var cubeMesh: Mesh
    private lateinit var shader: ShaderProgram
    private lateinit var cubeMVP: Mat4

    private var textureId: Int = 0

    override fun awake() {
        super.awake()

        shader = ShaderProgram(
            listOf(
                ShaderProgram.ShaderData(FileConstants.shaderFile("fragment.glsl").readText(), GL_FRAGMENT_SHADER),
                ShaderProgram.ShaderData(FileConstants.shaderFile("vertex.glsl").readText(), GL_VERTEX_SHADER)
            )
        )

        cubeMesh = Mesh(
            floatArrayOf(
                -1.0f, -1.0f, -1.0f, // triangle 1 : begin
                -1.0f, -1.0f, 1.0f,
                -1.0f, 1.0f, 1.0f, // triangle 1 : end
                1.0f, 1.0f, -1.0f, // triangle 2 : begin
                -1.0f, -1.0f, -1.0f,
                -1.0f, 1.0f, -1.0f, // triangle 2 : end
                1.0f, -1.0f, 1.0f,
                -1.0f, -1.0f, -1.0f,
                1.0f, -1.0f, -1.0f,
                1.0f, 1.0f, -1.0f,
                1.0f, -1.0f, -1.0f,
                -1.0f, -1.0f, -1.0f,
                -1.0f, -1.0f, -1.0f,
                -1.0f, 1.0f, 1.0f,
                -1.0f, 1.0f, -1.0f,
                1.0f, -1.0f, 1.0f,
                -1.0f, -1.0f, 1.0f,
                -1.0f, -1.0f, -1.0f,
                -1.0f, 1.0f, 1.0f,
                -1.0f, -1.0f, 1.0f,
                1.0f, -1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,
                1.0f, -1.0f, -1.0f,
                1.0f, 1.0f, -1.0f,
                1.0f, -1.0f, -1.0f,
                1.0f, 1.0f, 1.0f,
                1.0f, -1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, -1.0f,
                -1.0f, 1.0f, -1.0f,
                1.0f, 1.0f, 1.0f,
                -1.0f, 1.0f, -1.0f,
                -1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,
                -1.0f, 1.0f, 1.0f,
                1.0f, -1.0f, 1.0f
            ),
            floatArrayOf(
                0.583f, 0.771f, 0.014f,
                0.609f, 0.115f, 0.436f,
                0.327f, 0.483f, 0.844f,
                0.822f, 0.569f, 0.201f,
                0.435f, 0.602f, 0.223f,
                0.310f, 0.747f, 0.185f,
                0.597f, 0.770f, 0.761f,
                0.559f, 0.436f, 0.730f,
                0.359f, 0.583f, 0.152f,
                0.483f, 0.596f, 0.789f,
                0.559f, 0.861f, 0.639f,
                0.195f, 0.548f, 0.859f,
                0.014f, 0.184f, 0.576f,
                0.771f, 0.328f, 0.970f,
                0.406f, 0.615f, 0.116f,
                0.676f, 0.977f, 0.133f,
                0.971f, 0.572f, 0.833f,
                0.140f, 0.616f, 0.489f,
                0.997f, 0.513f, 0.064f,
                0.945f, 0.719f, 0.592f,
                0.543f, 0.021f, 0.978f,
                0.279f, 0.317f, 0.505f,
                0.167f, 0.620f, 0.077f,
                0.347f, 0.857f, 0.137f,
                0.055f, 0.953f, 0.042f,
                0.714f, 0.505f, 0.345f,
                0.783f, 0.290f, 0.734f,
                0.722f, 0.645f, 0.174f,
                0.302f, 0.455f, 0.848f,
                0.225f, 0.587f, 0.040f,
                0.517f, 0.713f, 0.338f,
                0.053f, 0.959f, 0.120f,
                0.393f, 0.621f, 0.362f,
                0.673f, 0.211f, 0.457f,
                0.820f, 0.883f, 0.371f,
                0.982f, 0.099f, 0.879f
            ),
            floatArrayOf(
                0.000059f, 1.0f - 0.000004f,
                0.000103f, 1.0f - 0.336048f,
                0.335973f, 1.0f - 0.335903f,
                1.000023f, 1.0f - 0.000013f,
                0.667979f, 1.0f - 0.335851f,
                0.999958f, 1.0f - 0.336064f,
                0.667979f, 1.0f - 0.335851f,
                0.336024f, 1.0f - 0.671877f,
                0.667969f, 1.0f - 0.671889f,
                1.000023f, 1.0f - 0.000013f,
                0.668104f, 1.0f - 0.000013f,
                0.667979f, 1.0f - 0.335851f,
                0.000059f, 1.0f - 0.000004f,
                0.335973f, 1.0f - 0.335903f,
                0.336098f, 1.0f - 0.000071f,
                0.667979f, 1.0f - 0.335851f,
                0.335973f, 1.0f - 0.335903f,
                0.336024f, 1.0f - 0.671877f,
                1.000004f, 1.0f - 0.671847f,
                0.999958f, 1.0f - 0.336064f,
                0.667979f, 1.0f - 0.335851f,
                0.668104f, 1.0f - 0.000013f,
                0.335973f, 1.0f - 0.335903f,
                0.667979f, 1.0f - 0.335851f,
                0.335973f, 1.0f - 0.335903f,
                0.668104f, 1.0f - 0.000013f,
                0.336098f, 1.0f - 0.000071f,
                0.000103f, 1.0f - 0.336048f,
                0.000004f, 1.0f - 0.671870f,
                0.336024f, 1.0f - 0.671877f,
                0.000103f, 1.0f - 0.336048f,
                0.336024f, 1.0f - 0.671877f,
                0.335973f, 1.0f - 0.335903f,
                0.667969f, 1.0f - 0.671889f,
                1.000004f, 1.0f - 0.671847f,
                0.667979f, 1.0f - 0.335851f
            )
        )

        textureId = Texture(File(FileConstants.game(), "test.png").path).loadTexture()
    }

    override fun render(deltaTime : Double) {
        super.render(deltaTime)

        cubeMVP = createMvpMatrix(
            createModelMatrix(
                Vec3(0, 0, 0), Vec3(0, 0, 0), Vec3(1, 1, 1)
            )
        )

        glEnable(GL_DEPTH_TEST)
        glDepthFunc(GL_LESS)

        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, textureId)

        shader.bind()

        val loc = GL20.glGetUniformLocation(shader.programId, "myTextureSampler")
        glUniform1i(loc, 0)

        shader.applyMvpMatrix(cubeMVP)

        cubeMesh.bind()
        cubeMesh.draw()

        shader.unbind()
    }

    override fun rest() {
        super.rest()
        shader.cleanup()
        cubeMesh.cleanup()
    }

}