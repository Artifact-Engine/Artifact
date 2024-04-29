package org.openartifact.artifact.core.graphics.window

class WindowProfile(
    val title : String,
    val targetFPS : Int,
    val targetUPS : Int,
    val width : Int,
    val height : Int,
    val aspectRatio : AspectRatio,
    val resizable : Boolean = true,
    var windowId : Long = 0,
)