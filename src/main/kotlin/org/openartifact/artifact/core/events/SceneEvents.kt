package org.openartifact.artifact.core.events

import org.openartifact.artifact.game.scene.Scene
import org.openartifact.artifact.core.event.Event

open class SceneEvent(val scene: Scene) : Event

/**
 * An event called when the scene gets first loaded
 */
class AwakeEvent(scene: Scene) : SceneEvent(scene)

/**
 * An event called when the scene unloads
 */
class RestEvent(val scene: Scene) : Event