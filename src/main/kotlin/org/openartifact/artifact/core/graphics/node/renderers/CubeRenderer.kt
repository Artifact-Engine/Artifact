package org.openartifact.artifact.core.graphics.node.renderers

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import org.lwjgl.opengl.GL30
import org.openartifact.artifact.core.graphics.Mesh
import org.openartifact.artifact.core.graphics.ShaderProgram
import org.openartifact.artifact.core.graphics.ShaderProgram.ShaderModuleData
import org.openartifact.artifact.core.graphics.node.NodeRenderer
import org.openartifact.artifact.game.nodes.CubeNode
import org.openartifact.artifact.utils.getShaderFile

class CubeRenderer : NodeRenderer<CubeNode>() {

    private lateinit var shaderProgram : ShaderProgram

    override fun initRender() {

        val shaderModuleDataList : MutableList<ShaderModuleData> = ArrayList()
        shaderModuleDataList.add(ShaderModuleData(getShaderFile("test.vert").readText(), GL20.GL_VERTEX_SHADER))
        shaderModuleDataList.add(ShaderModuleData(getShaderFile("test.frag").readText(), GL20.GL_FRAGMENT_SHADER))
        shaderProgram = ShaderProgram(shaderModuleDataList)

        val positions = floatArrayOf(
            -0.5f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            0.5f, 0.5f, 0.0f,
        )
        val colors = floatArrayOf(
            0.5f, 0.0f, 0.0f,
            0.0f, 0.5f, 0.0f,
            0.0f, 0.0f, 0.5f,
            0.0f, 0.5f, 0.5f,
        )

        val indices = intArrayOf(
            0, 1, 3, 3, 1, 2
        )

        val mesh = Mesh(positions, colors, indices)
        addMesh("triangle", mesh)
    }

    override fun render() {
        shaderProgram.bind()

        meshes.values.forEach { mesh ->
            GL30.glBindVertexArray(mesh.vaoId)
            GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.numVertices, GL11.GL_UNSIGNED_INT, 0)
        }

        GL30.glBindVertexArray(0)

        shaderProgram.unbind()
    }

    override fun cleanupRender() {

    }

}