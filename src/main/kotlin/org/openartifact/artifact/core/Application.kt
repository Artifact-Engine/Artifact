package org.openartifact.artifact.core

import org.openartifact.artifact.core.event.EventBus
import org.openartifact.artifact.graphics.interfaces.IVertexBuffer
import org.openartifact.artifact.graphics.Renderer
import org.openartifact.artifact.graphics.platform.opengl.OpenGLShader
import org.slf4j.Logger
import org.slf4j.LoggerFactory

open class Application {

    open val eventBus : EventBus = EventBus()
    open val logger : Logger = LoggerFactory.getLogger(javaClass)

    var vertexArray = 0
    lateinit var vertexBuffer : IVertexBuffer
    var indexBuffer = 0

    lateinit var renderer : Renderer

    var shader : OpenGLShader? = null

    open fun init() {}
    open fun update() {}
    open fun shutdown() {}

}