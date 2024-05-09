package org.openartifact.artifact.core

import org.openartifact.artifact.core.event.EventBus
import org.openartifact.artifact.graphics.interfaces.IVertexBuffer
import org.openartifact.artifact.graphics.Renderer
import org.openartifact.artifact.graphics.interfaces.IIndexBuffer
import org.openartifact.artifact.graphics.interfaces.IShader
import org.openartifact.artifact.graphics.interfaces.IVertexArray
import org.slf4j.Logger
import org.slf4j.LoggerFactory

open class Application {

    open val eventBus : EventBus = EventBus()
    open val logger : Logger = LoggerFactory.getLogger(javaClass)

    lateinit var vertexArray : IVertexArray
    lateinit var vertexBuffer : IVertexBuffer
    lateinit var indexBuffer : IIndexBuffer

    lateinit var renderer : Renderer

    var shader : IShader? = null

    open fun init() {}
    open fun update() {}
    open fun shutdown() {}

}