package com.neoutils.skiko

import com.neoutils.core.input.KeyEvent
import com.neoutils.core.scene.SceneManager
import com.neoutils.dsl.ScenesBuilder
import org.jetbrains.skiko.SkiaLayer
import org.jetbrains.skiko.SkiaLayerRenderDelegate
import java.awt.Dimension
import java.awt.event.KeyAdapter
import javax.swing.JFrame
import javax.swing.SwingUtilities
import javax.swing.WindowConstants

class SkikoWindow(
    private val title: String = "kmp-engine",
    private val config: WindowSize = WindowSize()
) {

    fun run(manager: SceneManager) {

        val skiaLayer = SkiaLayer()

        val delegate = SceneRenderDelegate(manager)

        skiaLayer.renderDelegate = SkiaLayerRenderDelegate(skiaLayer, delegate)

        skiaLayer.isFocusable = true
        skiaLayer.addKeyListener(object : KeyAdapter() {
            override fun keyPressed(e: java.awt.event.KeyEvent) {
                delegate.enqueue(KeyEvent(keyOf(e.keyCode), pressed = true))
            }

            override fun keyReleased(e: java.awt.event.KeyEvent) {
                delegate.enqueue(KeyEvent(keyOf(e.keyCode), pressed = false))
            }
        })

        SwingUtilities.invokeLater {
            val window = JFrame(title).apply {
                defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
                preferredSize = Dimension(config.width, config.height)
            }
            skiaLayer.attachTo(window.contentPane)
            skiaLayer.needRender()
            window.pack()
            window.setLocationRelativeTo(null)
            window.isVisible = true
            skiaLayer.requestFocusInWindow()
        }
    }
}

fun runSkikoWindow(
    title: String = "kmp-engine",
    size: WindowSize = WindowSize(),
    block: ScenesBuilder.() -> Unit,
) {
    val factories = ScenesBuilder().apply(block).factories
    SkikoWindow(title, size).run(SceneManager(factories))
}