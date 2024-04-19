package org.openartifact.core.events

import org.openartifact.core.event.Event

open class SceneEvent(val scene: Scene) : Event

/**
 * An event called when the scene gets first loaded
 */
class AwakeEvent(scene: Scene) : SceneEvent(scene)

/**
 * An event called when the scene unloads
 */
class RestEvent(val scene: Scene) : Event