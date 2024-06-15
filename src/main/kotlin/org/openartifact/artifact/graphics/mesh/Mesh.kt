/*
 * Copyright Artifact-Engine (c) 2024.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package org.openartifact.artifact.graphics.mesh

import org.lwjgl.assimp.AIMesh
import org.lwjgl.assimp.AIScene
import org.lwjgl.assimp.Assimp.*
import org.openartifact.artifact.core.Artifact
import org.openartifact.artifact.extensions.multiValuedMapOf
import org.openartifact.artifact.graphics.*
import org.openartifact.artifact.graphics.interfaces.*
import org.openartifact.artifact.graphics.platform.opengl.OpenGLShader
import org.openartifact.artifact.graphics.platform.opengl.ShaderType
import org.openartifact.artifact.resource.Resource
import org.openartifact.artifact.resource.resource
import org.openartifact.artifact.utils.calculateModelMatrix

class Mesh(private val resource : Resource) : IGraphicsComponent {

    private val vertices = mutableListOf<Float>()
    private val indices = mutableListOf<Int>()

    private val vertexArray: IVertexArray
    private val vertexBuffer: IVertexBuffer
    private val indexBuffer: IIndexBuffer

    private val shader : IShader

    init {
        val aiScene = loadScene()

        println(aiScene.mNumMeshes())

        val aiMeshes = aiScene.mMeshes()!!
        while (aiMeshes.hasRemaining()) {
            val aiMeshAddress = aiMeshes.get()
            process(AIMesh.create(aiMeshAddress))
        }

        aiReleaseImport(aiScene)

        val bufferLayout = renderer.choose<IBufferLayout>().create(
            multiValuedMapOf(
                DataType.Vec3 to "a_Position"
            )
        )

        vertexBuffer = renderer.choose<IVertexBuffer>().create(vertices.toFloatArray(), bufferLayout)

        indexBuffer = renderer.choose<IIndexBuffer>().create(indices.toIntArray())

        vertexArray = renderer.choose<IVertexArray>().create(
            vertexBuffer,
            indexBuffer
        )

        val vertex = resource("shaders/vertex.glsl")
        val fragment = resource("shaders/fragment.glsl")

        shader = renderer.choose<IShader>()
            .create(
                listOf(
                    OpenGLShader.ShaderModule(vertex.asText(), ShaderType.VERTEX),
                    OpenGLShader.ShaderModule(fragment.asText(), ShaderType.FRAGMENT),
                )
            )
    }

    private fun process(aiMesh : AIMesh) {
        val vertexBuffer = aiMesh.mVertices()
        while (vertexBuffer.hasRemaining()) {
            val vertex = vertexBuffer.get()
            vertices.add(vertex.x())
            vertices.add(vertex.y())
            vertices.add(vertex.z())
        }

        val indexBuffer = aiMesh.mFaces()
        for (j in 0 until indexBuffer.remaining()) {
            val face = indexBuffer.get()
            for (k in 0 until face.mNumIndices()) {
                indices.add(face.mIndices()[k])
            }
        }
    }

    private fun loadScene() : AIScene =
        aiImportFile(resource.extract().file.path, -1)!!

    override fun commit() {
        commit(shader) {
            parameterMat4("u_Projection", Camera.get().calculateProjectionMatrix())
            parameterMat4("u_View", Camera.get().calculateViewMatrix())
            parameterMat4("u_Model", calculateModelMatrix())
        }

        commit(vertexArray)
    }

}