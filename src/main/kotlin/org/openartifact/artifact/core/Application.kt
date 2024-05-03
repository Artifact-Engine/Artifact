package org.openartifact.artifact.core

import org.openartifact.artifact.core.event.EventBus
import org.slf4j.Logger
import org.slf4j.LoggerFactory

open class Application {

    open val eventBus : EventBus = EventBus()
    open val logger : Logger = LoggerFactory.getLogger(javaClass)

    open fun init() {}
    open fun update() {}
    open fun shutdown() {}

}