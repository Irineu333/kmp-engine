package com.neoutils.example

import com.neoutils.core.*
import com.neoutils.greeting
import com.neoutils.skiko.SceneRenderDelegate
import org.jetbrains.skiko.SkiaLayer
import org.jetbrains.skiko.SkiaLayerRenderDelegate
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.SwingUtilities
import javax.swing.WindowConstants

fun main() {

    // core

    val scene = SceneTree(
        root = Node().apply {
            add(
                CenteredNode2D().apply {
                    add(
                        CenterAlignLabel().apply {
                            text = "Hello, World!"
                        },
                    )
                },
            )
        },
    )

    // skiko

    val skiaLayer = SkiaLayer()

    skiaLayer.renderDelegate = SkiaLayerRenderDelegate(skiaLayer, SceneRenderDelegate(scene))

    SwingUtilities.invokeLater {
        val window = JFrame("hello world").apply {
            defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
            preferredSize = Dimension(800, 600)
        }
        skiaLayer.attachTo(window.contentPane)
        skiaLayer.needRender()
        window.pack()
        window.setLocationRelativeTo(null)
        window.isVisible = true
    }
}
