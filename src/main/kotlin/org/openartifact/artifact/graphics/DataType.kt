/*
 * Copyright Artifact-Engine (c) 2024.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package org.openartifact.artifact.graphics

import org.lwjgl.opengl.GL46.*

/**
 * Represents the data types used in OpenGL buffer layouts.
 * Each data type is associated with a size, a corresponding OpenGL type, and a byte size.
 */
enum class DataType(val size : Int, val glType : Int) {

    /**
     * A single float value.
     */
    Float(1, GL_FLOAT),

    /**
     * An integer value.
     */
    Integer(1, GL_INT),

    /**
     * A boolean value.
     */
    Boolean(1, GL_BOOL),

    /**
     * A vector of two floats.
     */
    Vec2(2, GL_FLOAT),

    /**
     * A vector of three floats.
     */
    Vec3(3, GL_FLOAT),

    /**
     * A vector of four floats.
     */
    Vec4(4, GL_FLOAT),

    /**
     * A 2x2 matrix of floats.
     */
    Mat2(2 * 2, GL_FLOAT),

    /**
     * A 3x3 matrix of floats.
     */
    Mat3(3 * 3, GL_FLOAT),

    /**
     * A 4x4 matrix of floats.
     */
    Mat4(4 * 4, GL_FLOAT);

    /**
     * Calculates the byte size of a org.openartifact.artifact.graphics.DataType instance dynamically.
     *
     * @return The byte size of the org.openartifact.artifact.graphics.DataType instance.
     */
    fun byteSize() : Int =
        size * when (glType) {
            GL_INT -> Int.SIZE_BYTES
            GL_FLOAT -> kotlin.Float.SIZE_BYTES
            GL_DOUBLE -> Double.SIZE_BYTES
            GL_BOOL -> Byte.SIZE_BYTES
            else -> throw IllegalArgumentException("Unsupported GL type: $glType")
        }
}