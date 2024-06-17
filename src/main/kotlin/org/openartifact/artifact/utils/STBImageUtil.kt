package org.openartifact.artifact.utils

import org.lwjgl.stb.STBImage
import org.lwjgl.system.MemoryStack
import org.openartifact.artifact.resource.Resource
import java.nio.ByteBuffer

class STBImageUtil(resource: Resource) {

    var width = 0
    var height = 0
    var image : ByteBuffer

    init {
        MemoryStack.stackPush().use { stack ->
            val comp = stack.mallocInt(1)
            val w = stack.mallocInt(1)
            val h = stack.mallocInt(1)

            image = STBImage.stbi_load(resource.extract()
                .path.toString(), comp, w, h, 4)!!

            width = w.get()
            height = h.get()
        }
    }

}