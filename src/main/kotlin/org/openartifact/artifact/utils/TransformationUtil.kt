package org.openartifact.artifact.utils

import glm_.func.toRadians
import glm_.glm
import glm_.mat4x4.Mat4
import glm_.vec3.Vec3
import org.openartifact.artifact.core.Application
import org.openartifact.artifact.game.nodes.DynamicBodyNode


fun createProjectionMatrix(fov : Float, aspectRatio : Float, zNear : Float, zFar : Float) : Mat4 =
    glm.perspective(glm.radians(fov), aspectRatio, zNear, zFar)

fun createViewMatrix(cameraPosition : Vec3, cameraTarget : Vec3, upVector : Vec3) : Mat4 =
    glm.lookAt(cameraPosition, cameraTarget, upVector)

fun createModelMatrix(dynamicBodyNode : DynamicBodyNode) : Mat4 {
    val modelViewMatrix = Mat4()

    val rotation : Vec3 = dynamicBodyNode.rotation
    modelViewMatrix.identity()
        .translateAssign(dynamicBodyNode.position)
        .rotateXassign(toRadians(-rotation.x))
        .rotateYassign(toRadians(-rotation.y))
        .rotateZassign(toRadians(-rotation.z))
        .scaleAssign(dynamicBodyNode.scale)

    return modelViewMatrix
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
    Application.current().sceneManager.activeScene !!.camera.getViewMatrix()

fun createMvpMatrix(projectionMatrix : Mat4, modelMatrix : Mat4) : Mat4 =
    createMvpMatrix(projectionMatrix, getViewMatrix(), modelMatrix)

fun createMvpMatrix(modelMatrix : Mat4) : Mat4 =
    createMvpMatrix(Application.current().sceneManager.activeScene !!.camera.getProjectionMatrix(), modelMatrix)