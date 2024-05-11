package org.openartifact.artifact.core

import org.openartifact.artifact.graphics.Renderer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

open class Application {

    open val logger : Logger = LoggerFactory.getLogger(javaClass)

    lateinit var renderer : Renderer

    open fun init() {}
    open fun update() {}
    open fun shutdown() {}

}