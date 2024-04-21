package org.openartifact.artifact.core.event.events

import org.openartifact.artifact.core.Engine
import org.openartifact.artifact.core.event.Event
import org.openartifact.artifact.game.Component
import org.openartifact.artifact.game.Node
import kotlin.reflect.KClass

class ComponentRegisterEvent : Event {

    fun register(kClass : KClass<out Component>) {
        Engine.componentClasses.add(kClass)
    }

}

class NodeRegisterEvent : Event {

    fun register(kClass : KClass<out Node>) {
        Engine.nodeClasses.add(kClass)
    }

}