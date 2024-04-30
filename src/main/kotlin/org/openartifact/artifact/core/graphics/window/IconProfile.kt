package org.openartifact.artifact.core.graphics.window

import org.openartifact.artifact.utils.FileConstants
import java.io.File

class IconProfile(
    val file : File = File(FileConstants.engineData(), "artifact48.png"),
    val width : Int = 48,
    val height : Int = 48
)