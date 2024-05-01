package org.openartifact.artifact.core.graphics.mesh

import java.io.File
import java.util.ArrayList

object OBJLoader {

    fun loadMesh(objFile : File): Mesh {
        val vertices = ArrayList<Float>()
        val texCoords = ArrayList<Float>()
        val normals = ArrayList<Float>()
        val indices = ArrayList<Int>()

        objFile.readText().split("\n").forEach { line ->
            when {
                line.startsWith("v ") -> { // Vertex
                    val parts = line.split("\\s+".toRegex())
                    vertices.addAll(parts.subList(1, parts.size).map { it.toFloat() })
                }
                line.startsWith("vt ") -> { // Texture Coordinate
                    val parts = line.split("\\s+".toRegex())
                    texCoords.addAll(parts.subList(1, parts.size).map { it.toFloat() })
                }
                line.startsWith("vn ") -> { // Normal
                    val parts = line.split("\\s+".toRegex())
                    normals.addAll(parts.subList(1, parts.size).map { it.toFloat() })
                }
                line.startsWith("f ") -> { // Face
                    val parts = line.split("\\s+".toRegex())
                    for (i in 1 until parts.size) {
                        val vertexParts = parts[i].split("/")
                        indices.add(vertexParts[0].toInt() - 1) // Subtract 1 because OBJ uses 1-based indexing
                    }
                }
            }
        }

        val vertexData = FloatArray(vertices.size)
        val texCoordsData = FloatArray(texCoords.size)
        val normalsData = FloatArray(normals.size)
        val indexData = IntArray(indices.size)

        vertices.forEachIndexed { index, value -> vertexData[index] = value }
        texCoords.forEachIndexed { index, value -> texCoordsData[index] = value }
        normals.forEachIndexed { index, value -> normalsData[index] = value }
        indices.forEachIndexed { index, value -> indexData[index] = value }

        return Mesh(vertexData, texCoordsData, normalsData, indexData)
    }
}
