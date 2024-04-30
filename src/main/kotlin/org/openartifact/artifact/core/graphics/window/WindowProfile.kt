package org.openartifact.artifact.core.graphics.window

import org.lwjgl.glfw.GLFW

/**
 * Represents the profile of a window in a game.
 *
 * This class encapsulates various properties that define the behavior and appearance of a window,
 * such as its title, dimensions, aspect ratio, and whether it is resizable.
 *
 * @property title The title of the window, displayed in the window's title bar.
 * @property targetFPS The target frames per second (FPS) for the window. Setting this to 0 indicates that the application should attempt to render as many frames as possible, potentially at the expense of CPU usage.
 * @property targetUPS The target updates per second (UPS) for the window. Setting this to 0 will default to the value of `targetFPS`, effectively synchronizing the update rate with the frame rate.
 * @property width The width of the window in pixels. This value, along with `height`, defines the window's size.
 * @property height The height of the window in pixels. This value, along with `width`, defines the window's size.
 * @property aspectRatio The aspect ratio of the window, which is the ratio of its width to its height. This is used to maintain the window's proportions when resizing.
 * @property iconProfile The profile of the icon to be used for the window. This includes the icon's image data and any associated metadata. Defaults to an empty icon profile, which may result in a default system icon being used.
 * @property resizable Whether the window can be resized by the user. Defaults to true, allowing the user to adjust the window's size.
 * @property windowId A unique identifier for the window, used internally by the application to track and manage windows. Defaults to 0, which may be interpreted as an uninitialized or invalid ID.
 *
 * @constructor Creates a new WindowProfile with the specified properties.
 * @throws IllegalArgumentException if `targetUPS` is not greater than `targetFPS` when `targetUPS` is not 0. This ensures that the update rate is not faster than the frame rate, which could lead to performance issues or visual artifacts.
 */
class WindowProfile(
    val title: String,
    var targetFPS: Int,
    var targetUPS: Int,
    val width: Int,
    val height: Int,
    val aspectRatio: AspectRatio = AspectRatio.findClosestAspectRatio(width, height)!!,
    val iconProfile: IconProfile = IconProfile(),
    val resizable: Boolean = true,
    var windowId: Long = 0,
)
