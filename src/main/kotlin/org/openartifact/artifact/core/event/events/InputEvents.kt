package org.openartifact.artifact.core.event.events

import org.openartifact.artifact.core.event.Event

class KeyPressEvent(val key: Int) : Event
class KeyReleaseEvent(val key: Int) : Event
class KeyRepeatEvent(val key: Int) : Event