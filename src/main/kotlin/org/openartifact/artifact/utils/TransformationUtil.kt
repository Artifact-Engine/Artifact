package org.openartifact.artifact.utils

import glm_.func.toRadians
import glm_.glm
import glm_.mat4x4.Mat4
import glm_.vec3.Vec3
import org.lwjgl.opengl.GL20
import org.openartifact.artifact.core.Context
import org.openartifact.artifact.core.graphics.Mesh
import org.openartifact.artifact.core.graphics.ShaderProgram
import org.openartifact.artifact.core.graphics.component.MeshRenderer


fun createProjectionMatrix(fov : Float, aspectRatio : Float, zNear : Float, zFar : Float) : Mat4 =
    glm.perspective(glm.radians(fov), aspectRatio, zNear, zFar)

fun createViewMatrix(cameraPosition : Vec3, cameraTarget : Vec3, upVector : Vec3) : Mat4 =
    glm.lookAt(cameraPosition, cameraTarget, upVector)

fun createModelMatrixOld(translation : Vec3, rotation : Vec3, scale : Vec3) : Mat4 {
    var modelMatrix = Mat4(1.0f)
    modelMatrix = glm.scale(modelMatrix, scale)
    modelMatrix = glm.rotate(modelMatrix, rotation.x, Vec3(1.0f, 0.0f, 0.0f))
    modelMatrix = glm.rotate(modelMatrix, rotation.y, Vec3(0.0f, 1.0f, 0.0f))
    modelMatrix = glm.rotate(modelMatrix, rotation.z, Vec3(0.0f, 0.0f, 1.0f))
    modelMatrix = glm.translate(modelMatrix, translation)
    return modelMatrix
}

fun createModelMatrix(meshRenderer : MeshRenderer) : Mat4 {
    val node = meshRenderer.staticBodyNode

    val rotation = node.rotation
    val modelViewMatrix = Mat4(1).identity()
        .translateAssign(node.position)
        .rotateXassign(- rotation.x)
        .rotateYassign(- rotation.y)
        .rotateZassign(- rotation.z)
        .scaleAssign(node.scale)

    val viewCurr = getViewMatrix()
    return viewCurr * modelViewMatrix
}

fun createWorldMatrix(offset : Vec3, rotation : Vec3, scale : Float) : Mat4 {
    val worldMatrix = Mat4(1).identity()
        .translateAssign(offset)
        .rotateXassign(toRadians(rotation.x))
        .rotateYassign(toRadians(rotation.y))
        .rotateZassign(toRadians(rotation.z))
    return worldMatrix
}

fun createMvpMatrix(projectionMatrix : Mat4, viewMatrix : Mat4, modelMatrix : Mat4) : Mat4 =
    projectionMatrix * viewMatrix * modelMatrix

fun getViewMatrix() : Mat4 =
    Context.current().sceneManager.activeScene !!.camera.getViewMatrix()

fun createMvpMatrix(projectionMatrix : Mat4, modelMatrix : Mat4) : Mat4 =
    createMvpMatrix(projectionMatrix, getViewMatrix(), modelMatrix)

fun createMvpMatrix(modelMatrix : Mat4) : Mat4 =
    createMvpMatrix(Context.current().sceneManager.activeScene !!.camera.getProjectionMatrix(), modelMatrix)