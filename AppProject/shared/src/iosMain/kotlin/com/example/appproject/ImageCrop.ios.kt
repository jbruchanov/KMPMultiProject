package com.example.appproject

import org.jetbrains.skia.Data
import org.jetbrains.skia.IRect
import org.jetbrains.skia.Image
import org.jetbrains.skia.ImageInfo
import org.jetbrains.skia.Pixmap

actual fun crop(image: Any, step: Int): Any {
    with(image as Image) {
        val targetWidth = (image.width - (step * 2))
        val targetHeight = (image.height - (step * 2))
        val bytesPerPixel = imageInfo.colorInfo.bytesPerPixel
        println("Crop, tw:$targetWidth, th:${targetHeight}, step:$step, bytesPerPixel:$bytesPerPixel")

        val rowBytes = targetWidth * bytesPerPixel
        val necessaryMemory = targetHeight * rowBytes
        val data = Data.makeUninitialized(necessaryMemory)
        require(data.size > 0) { "Invalid data size" }
        val output = Pixmap.make(
            info = ImageInfo(
                colorInfo = imageInfo.colorInfo,
                width = targetWidth,
                height = targetHeight
            ),
            buffer = data,
            rowBytes = rowBytes
        )
        return if (readPixels(dst = output, srcX = step, srcY = step, cache = false)) {
            Image.makeFromPixmap(output).also {
                output.close()
            }
        } else {
            throw IllegalStateException("Unable to crop it, readPixels() returned false")
        }
    }
}