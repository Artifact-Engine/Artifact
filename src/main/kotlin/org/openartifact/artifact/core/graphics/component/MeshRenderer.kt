package org.openartifact.artifact.core.graphics.component

import glm_.mat4x4.Mat4
import glm_.vec3.Vec3
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30.*
import org.openartifact.artifact.core.graphics.Mesh
import org.openartifact.artifact.core.graphics.ShaderProgram
import org.openartifact.artifact.core.graphics.Texture
import org.openartifact.artifact.game.Component
import org.openartifact.artifact.game.nodes.StaticBodyNode
import org.openartifact.artifact.utils.FileConstants
import org.openartifact.artifact.utils.createModelMatrix
import org.openartifact.artifact.utils.createModelMatrixOld
import org.openartifact.artifact.utils.createMvpMatrix
import java.io.File

open class MeshRenderer(open var mesh : Mesh) : Component() {

    lateinit var staticBodyNode : StaticBodyNode
    private lateinit var mvp : Mat4
    private lateinit var shader : ShaderProgram
    private lateinit var texture : Texture

    override fun awake() {
        staticBodyNode = parent as StaticBodyNode

        shader = ShaderProgram(
            listOf(
                ShaderProgram.ShaderData(FileConstants.shaderFile("fragment.glsl").readText(), GL_FRAGMENT_SHADER),
                ShaderProgram.ShaderData(FileConstants.shaderFile("vertex.glsl").readText(), GL_VERTEX_SHADER)
            )
        )

        texture = Texture(File(FileConstants.game(), "test.png").path)
    }

    override fun render(deltaTime : Double) {
        val modelMatrix = createModelMatrix(this)

        mvp = createMvpMatrix(modelMatrix)

        glEnable(GL_DEPTH_TEST)
        glDepthFunc(GL_LESS)

        texture.render()

        shader.bind()

        val loc = GL20.glGetUniformLocation(shader.programId, "myTextureSampler")
        glUniform1i(loc, 0)

        shader.applyMvpMatrix(mvp)

        mesh.bind()
        mesh.draw()

        shader.unbind()
    }

    override fun rest() {
        shader.cleanup()
        mesh.cleanup()
    }

}