package com.neoutils.skiko

import com.neoutils.core.app.LaunchConfig
import com.neoutils.core.app.Launcher
import com.neoutils.core.scene.SceneTree
import org.jetbrains.skiko.SkiaLayer
import org.jetbrains.skiko.SkiaLayerRenderDelegate
import java.awt.Dimension
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.JFrame
import javax.swing.SwingUtilities
import javax.swing.WindowConstants
import com.neoutils.core.input.KeyEvent as CoreKeyEvent

class SkikoLauncher : Launcher {

    override fun launch(scene: SceneTree, config: LaunchConfig) {

        val skiaLayer = SkiaLayer()

        val delegate = SceneRenderDelegate(scene)

        skiaLayer.renderDelegate = SkiaLayerRenderDelegate(skiaLayer, delegate)

        skiaLayer.isFocusable = true
        skiaLayer.addKeyListener(object : KeyAdapter() {
            override fun keyPressed(e: KeyEvent) {
                delegate.enqueue(CoreKeyEvent(keyOf(e.keyCode), pressed = true))
            }

            override fun keyReleased(e: KeyEvent) {
                delegate.enqueue(CoreKeyEvent(keyOf(e.keyCode), pressed = false))
            }
        })

        SwingUtilities.invokeLater {
            val window = JFrame(config.title).apply {
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
