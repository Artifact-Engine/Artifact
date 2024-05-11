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
     * Calculates the byte size of a DataType instance dynamically.
     *
     * @return The byte size of the DataType instance.
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