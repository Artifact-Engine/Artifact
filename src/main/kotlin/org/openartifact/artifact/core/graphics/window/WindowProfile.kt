package org.openartifact.artifact.core.graphics.window

import org.openartifact.artifact.utils.getEngineDataDir
import java.io.File

class WindowProfile(
    val title : String,
    val targetFPS : Int,
    val targetUPS : Int,
    val width : Int,
    val height : Int,
    val aspectRatio : AspectRatio,
    val iconFile : File = File(getEngineDataDir(), "artifact.png"),
    val resizable : Boolean = true,
    var windowId : Long = 0,
)