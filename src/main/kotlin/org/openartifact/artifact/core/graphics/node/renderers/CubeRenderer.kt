package org.openartifact.artifact.core.graphics.node.renderers

import glm_.mat4x4.Mat4
import glm_.vec3.Vec3
import org.lwjgl.opengl.GL30.*
import org.openartifact.artifact.core.GameContext
import org.openartifact.artifact.core.graphics.Mesh
import org.openartifact.artifact.core.graphics.ShaderProgram
import org.openartifact.artifact.core.graphics.node.NodeRenderer
import org.openartifact.artifact.game.nodes.CubeNode
import org.openartifact.artifact.utils.*

class CubeRenderer : NodeRenderer<CubeNode>() {

    private lateinit var cubeMesh: Mesh
    private lateinit var triangleMesh: Mesh
    private lateinit var shader: ShaderProgram
    private lateinit var cubeMVP: Mat4
    private lateinit var triangleMVP: Mat4

    override fun initRender() {
        shader = ShaderProgram(listOf(
            ShaderProgram.ShaderData(getShaderFile("test.frag").readText(), GL_FRAGMENT_SHADER),
            ShaderProgram.ShaderData(getShaderFile("test.vert").readText(), GL_VERTEX_SHADER)
        ))

        cubeMesh = Mesh(
            floatArrayOf(
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
            ),
            floatArrayOf(
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
        )

        triangleMesh = Mesh(
            floatArrayOf(
                -1.0f, -1.0f, 0.0f,
                1.0f, -1.0f, 0.0f,
                0.0f,  1.0f, 0.0f,
            ),
            floatArrayOf(
                randomFloat(0..1), randomFloat(0..1), randomFloat(0..1),
                randomFloat(0..1), randomFloat(0..1), randomFloat(0..1),
                randomFloat(0..1), randomFloat(0..1), randomFloat(0..1),
            )
        )

        val projectionMatrix = createProjectionMatrix(60.0f, 4.0f / 3.0f, 0.1f, 100.0f)

        val cubeModelMatrix = createModelMatrix(Vec3(0, 0, 0), Vec3(0, 0, 0), Vec3(1, 1, 1))
        val triangleModelMatrix = createModelMatrix(Vec3(0, 0, 2), Vec3(0, 0, 0), Vec3(1, 1, 1))

        cubeMVP = createMvpMatrix(projectionMatrix, cubeModelMatrix)
        triangleMVP = createMvpMatrix(projectionMatrix, triangleModelMatrix)
    }

    override fun render() {
        glEnable(GL_DEPTH_TEST)
        glDepthFunc(GL_LESS)

        shader.bind()

        applyMvpMatrixToShader(shader, cubeMVP)

        cubeMesh.bind()
        cubeMesh.draw()

        applyMvpMatrixToShader(shader, triangleMVP)

        triangleMesh.bind()
        triangleMesh.draw()

        // LSD mode
        triangleMesh.modifyBufferData(triangleMesh.colorBuffer, floatArrayOf(
            randomFloat(0..1), randomFloat(0..1), randomFloat(0..1),
            randomFloat(0..1), randomFloat(0..1), randomFloat(0..1),
            randomFloat(0..1), randomFloat(0..1), randomFloat(0..1),
        ))

        shader.unbind()
    }

    override fun cleanupRender() {
        cubeMesh.cleanup()
    }
}