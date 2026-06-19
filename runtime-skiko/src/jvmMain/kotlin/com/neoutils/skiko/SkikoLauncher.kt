package com.neoutils.skiko

import com.neoutils.core.app.LaunchConfig
import com.neoutils.core.app.Launcher
import com.neoutils.core.scene.SceneTree
import org.jetbrains.skiko.SkiaLayer
import org.jetbrains.skiko.SkiaLayerRenderDelegate
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.SwingUtilities
import javax.swing.WindowConstants

class SkikoLauncher : Launcher {

    override fun launch(scene: SceneTree, config: LaunchConfig) {

        val skiaLayer = SkiaLayer()

        skiaLayer.renderDelegate = SkiaLayerRenderDelegate(
            skiaLayer,
            SceneRenderDelegate(scene),
        )

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
        }
    }
}
