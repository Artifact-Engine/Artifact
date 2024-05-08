package org.openartifact.artifact.graphics.platform.opengl

import glm_.vec2.Vec2
import glm_.vec3.Vec3
import glm_.vec4.Vec4
import org.lwjgl.opengl.GL46.*
import org.openartifact.artifact.graphics.interfaces.IBufferLayout
import kotlin.reflect.KClass

/**
 * Represents a buffer layout for OpenGL.
 */
class OpenGLBufferLayout : IBufferLayout {

    // Map to store attribute names and their corresponding data types and sizes
    private val dataMap = mutableMapOf<String, Pair<String, Int>>()

    /**
     * Creates a buffer layout based on the provided mapping of data classes to attribute names.
     * @param map A map of data classes to attribute names.
     * @return This OpenGLBufferLayout instance.
     * @throws IllegalArgumentException If an unsupported data type is provided.
     */
    override fun create(map : Map<KClass<*>, String>) : IBufferLayout {
        require(map.isNotEmpty()) { "Map cannot be empty." }

        map.entries.forEachIndexed { i, (dataClass, attributeName) ->
            val dataType = generateDataType(dataClass)
            dataMap[attributeName] = dataType

            println("$i -> ${dataType.first}")
            glEnableVertexAttribArray(i)
            glVertexAttribPointer(i, dataType.second, GL_FLOAT, false, dataType.second * Float.SIZE_BYTES, 0)
            // Error checking
            check(glGetError() == GL_NO_ERROR) { "OpenGL error occurred." }
        }

        return this
    }

    /**
     * Generates the GLSL data type and size based on the provided data class.
     * @param dataClass The data class.
     * @return Pair containing the GLSL data type and size.
     * @throws IllegalArgumentException If the data class is not supported.
     */
    private fun generateDataType(dataClass : KClass<*>) : Pair<String, Int> {
        return when (dataClass) {
            Vec3::class -> Pair("vec3", 3)
            Vec2::class -> Pair("vec2", 2)
            Vec4::class -> Pair("vec4", 4)
            else -> throw IllegalArgumentException("${dataClass.simpleName} is not a valid GLSL data type for OpenGLBufferLayout!")
        }
    }
}
