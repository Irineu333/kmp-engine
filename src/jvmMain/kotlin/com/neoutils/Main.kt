package com.neoutils

import org.jetbrains.skiko.SkiaLayer
import org.jetbrains.skiko.SkiaLayerRenderDelegate
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.SwingUtilities
import javax.swing.WindowConstants

fun main() {
    // SkiaLayer is the AWT component that exposes a GPU-accelerated Skia canvas.
    val skiaLayer = SkiaLayer()

    // SkiaLayerRenderDelegate wraps the scene and fixes content-scale (HiDPI/Retina displays).
    skiaLayer.renderDelegate = SkiaLayerRenderDelegate(skiaLayer, HelloWorldScene())

    SwingUtilities.invokeLater {
        val window = JFrame("hello world").apply {
            defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
            preferredSize = Dimension(800, 600)
        }
        skiaLayer.attachTo(window.contentPane)
        skiaLayer.needRender()
        window.pack()
        // Center the window on the screen (must be called after pack() so the size is known).
        window.setLocationRelativeTo(null)
        window.isVisible = true
    }
}
