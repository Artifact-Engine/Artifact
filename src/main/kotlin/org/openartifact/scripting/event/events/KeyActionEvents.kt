package org.openartifact.scripting.event.events

import org.openartifact.scripting.event.Event

class KeyPressEvent(val key: Int, val scancode: Int): Event
class KeyReleaseEvent(val key: Int, val scancode: Int): Event
class KeyRepeatEvent(val key: Int, val scancode: Int): Event
