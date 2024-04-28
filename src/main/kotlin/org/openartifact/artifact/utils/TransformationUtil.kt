package org.openartifact.artifact.utils

import glm_.glm
import glm_.mat4x4.Mat4
import glm_.vec3.Vec3
import org.lwjgl.opengl.GL20
import org.openartifact.artifact.core.GameContext
import org.openartifact.artifact.core.graphics.ShaderProgram

fun createProjectionMatrix(fov : Float, aspectRatio : Float, zNear : Float, zFar : Float) : Mat4 {
    return glm.perspective(glm.radians(fov), aspectRatio, zNear, zFar)
}

fun createViewMatrix(cameraPosition : Vec3, cameraTarget : Vec3, upVector : Vec3) : Mat4 {
    return glm.lookAt(cameraPosition, cameraTarget, upVector)
}

fun createModelMatrix(translation : Vec3, rotation : Vec3, scale : Vec3) : Mat4 {
    var modelMatrix = Mat4(1.0f)
    modelMatrix = glm.translate(modelMatrix, translation)
    modelMatrix = glm.rotate(modelMatrix, rotation.x, Vec3(1.0f, 0.0f, 0.0f))
    modelMatrix = glm.rotate(modelMatrix, rotation.y, Vec3(0.0f, 1.0f, 0.0f))
    modelMatrix = glm.rotate(modelMatrix, rotation.z, Vec3(0.0f, 0.0f, 1.0f))
    modelMatrix = glm.scale(modelMatrix, scale)
    return modelMatrix
}

fun createMvpMatrix(projectionMatrix : Mat4, viewMatrix : Mat4, modelMatrix : Mat4) : Mat4 =
    projectionMatrix * viewMatrix * modelMatrix

fun getViewMatrix() : Mat4 =
    GameContext.current().sceneManager.activeScene!!.camera.getViewMatrix()

fun createMvpMatrix(projectionMatrix : Mat4, modelMatrix : Mat4) : Mat4 =
    createMvpMatrix(projectionMatrix, getViewMatrix(), modelMatrix)

fun applyMvpMatrixToShader(shaderProgram : ShaderProgram, mvpMatrix : Mat4) {
    val mvpMatrixLocation = GL20.glGetUniformLocation(shaderProgram.programId, "MVP")
    GL20.glUniformMatrix4fv(mvpMatrixLocation, false, mvpMatrix.array)
}
