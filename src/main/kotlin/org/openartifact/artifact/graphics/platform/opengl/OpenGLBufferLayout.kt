package org.openartifact.artifact.graphics.platform.opengl

import glm_.mat2x2.Mat2
import glm_.mat3x3.Mat3
import glm_.mat4x4.Mat4
import glm_.vec2.Vec2
import glm_.vec2.Vec2d
import glm_.vec2.Vec2i
import glm_.vec3.Vec3
import glm_.vec3.Vec3d
import glm_.vec3.Vec3i
import glm_.vec4.Vec4
import glm_.vec4.Vec4d
import glm_.vec4.Vec4i
import org.lwjgl.opengl.GL46.*
import org.openartifact.artifact.graphics.interfaces.IBufferLayout
import kotlin.reflect.KClass

/**
 * Represents a buffer layout for OpenGL.
 */

private typealias GLData = Pair<Int, Int> // Size, GL Data Type

class OpenGLBufferLayout : IBufferLayout {

    // Map to store attribute names and their corresponding GLData
    private val attributeSizeMap = mutableMapOf<String, GLData>()

    /**
     * Creates a buffer layout based on the provided mapping of data classes to attribute names.
     * @param map A map of data classes to attribute names.
     * @return This [OpenGLBufferLayout] instance.
     * @throws IllegalArgumentException If an unsupported data type is provided.
     */
    override fun create(map: Map<KClass<*>, Pair<String, Boolean>>): IBufferLayout {
        require(map.isNotEmpty()) { "Map cannot be empty." }

        map.entries.forEachIndexed { i, (dataClass, pair) ->
            val (attributeName, normalize) = pair
            val glData = generateGLData(dataClass)
            attributeSizeMap[attributeName] = glData

            glEnableVertexAttribArray(i)
            glVertexAttribPointer(i, glData.first, glData.second, normalize, glData.first * Float.SIZE_BYTES, 0)

            // Error checking
            val errorCode = glGetError()
            if (errorCode != GL_NO_ERROR) {
                throw IllegalStateException("OpenGL error ($errorCode) occurred while setting up vertex attribute '$attributeName'.\n" +
                        "See https://www.khronos.org/opengl/wiki/OpenGL_Error#Meaning_of_errors for more information.")
            }
        }

        return this
    }

    /**
     * Generates the size of the GLSL attribute based on the provided data class.
     *
     * This method is used internally by the [OpenGLBufferLayout] to map Kotlin data classes
     * to their corresponding GLSL attribute sizes. The mapping is crucial for correctly setting up vertex attributes in OpenGL.
     *
     * The size of a vector is defined by how many components it has. For example, a [Vec3] has 3 components and thus a size of 3.
     * The size of a matrix is defined by how many columns it has. For example, a [Mat3] has 3 columns and thus a size of 3.
     *
     * @param dataClass The Kotlin class representing the data type. This should be one of the supported GLSL data types.
     * @return The size of the GLSL attribute as an Int.
     * @throws IllegalArgumentException If the provided data class is not one of the supported GLSL types.
     */
    private fun generateGLData(dataClass : KClass<*>) : Pair<Int, Int> {
        return when (dataClass) {
            // Float- Vectors
            Float::class ->  1 to GL_FLOAT
            Vec2::class ->   2 to GL_FLOAT
            Vec3::class ->   3 to GL_FLOAT
            Vec4::class ->   3 to GL_FLOAT

            // Integer- Vectors
            Int::class ->    1 to GL_INT
            Vec2i::class ->  2 to GL_INT
            Vec3i::class ->  3 to GL_INT
            Vec4i::class ->  4 to GL_INT

            // Double- Vectors
            Double::class -> 1 to GL_DOUBLE
            Vec2d::class ->  2 to GL_DOUBLE
            Vec3d::class ->  3 to GL_DOUBLE
            Vec4d::class ->  4 to GL_DOUBLE

            // Matrices
            Mat2::class ->   2 to GL_FLOAT
            Mat3::class ->   3 to GL_FLOAT
            Mat4::class ->   4 to GL_FLOAT

            // Boolean
            Boolean::class ->4 to GL_BOOL

            else -> throw IllegalArgumentException("${dataClass.simpleName} is not a valid GLSL data type!")
        }
    }
}
