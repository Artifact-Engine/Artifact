package org.openartifact.artifact.core

import org.openartifact.artifact.core.event.EventBus
import org.openartifact.artifact.graphics.platform.opengl.Shader
import org.slf4j.Logger
import org.slf4j.LoggerFactory

open class Application {

    open val eventBus : EventBus = EventBus()
    open val logger : Logger = LoggerFactory.getLogger(javaClass)

    var vertexArray = 0
    var vertexBuffer = 0
    var indexBuffer = 0

    var shader : Shader? = null

    open fun init() {}
    open fun update() {}
    open fun shutdown() {}

}