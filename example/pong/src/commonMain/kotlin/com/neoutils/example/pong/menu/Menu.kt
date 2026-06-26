package com.neoutils.example.pong.menu

import com.neoutils.core.graphics.Color
import com.neoutils.core.graphics.Renderer
import com.neoutils.core.input.Key
import com.neoutils.core.math.Rect
import com.neoutils.core.math.Size
import com.neoutils.core.math.Vec2
import com.neoutils.core.scene.Node
import com.neoutils.core.scene.Node2D
import com.neoutils.example.pong.game.GameMode

/**
 * Vertical list of game modes, centred on the viewport. Navigate with UP/DOWN,
 * confirm with ENTER; the chosen [GameMode] is handed to the "pong" scene.
 */
class Menu : Node() {

    private data class Option(val label: String, val mode: GameMode)

    private val options = listOf(
        Option("Player vs Player", GameMode.PLAYER_VS_PLAYER),
        Option("Player vs AI", GameMode.PLAYER_VS_AI),
        Option("AI vs AI", GameMode.AI_VS_AI),
    )

    private var selected = 0

    override fun onProcess(delta: Float) {
        val input = engine?.input ?: return
        if (input.isJustPressed(Key.UP)) {
            selected = (selected - 1 + options.size) % options.size
        }
        if (input.isJustPressed(Key.DOWN)) {
            selected = (selected + 1) % options.size
        }
        if (input.isJustPressed(Key.ENTER)) {
            engine?.changeScene("pong", options[selected].mode)
        }
    }

    override fun onDraw(renderer: Renderer) {
        val viewport = engine?.size ?: return
        val measurer = engine?.textMeasurer ?: return

        val optionHeight = measurer.measure(options[0].label, OPTION_SIZE).height
        val blockHeight = (options.size - 1) * LINE_HEIGHT + optionHeight
        val startY = (viewport.height - blockHeight) / 2f

        options.forEachIndexed { index, option ->
            val highlighted = index == selected
            val size = measurer.measure(option.label, OPTION_SIZE)
            val x = (viewport.width - size.width) / 2f
            val y = startY + index * LINE_HEIGHT

            if (highlighted) {
                renderer.drawRect(
                    rect = Rect(
                        position = Vec2(x - PAD_X, y - PAD_Y),
                        size = Size(size.width + PAD_X * 2f, optionHeight + PAD_Y * 2f),
                    ),
                    color = HIGHLIGHT,
                    fill = true,
                )
            }

            renderer.drawText(
                text = option.label,
                position = Vec2(x, y),
                size = OPTION_SIZE,
                color = if (highlighted) Color.BLACK else INACTIVE,
            )
        }
    }

    companion object {
        private const val OPTION_SIZE = 22f
        private const val LINE_HEIGHT = 36f
        private const val PAD_X = 16f
        private const val PAD_Y = 6f

        private val HIGHLIGHT = Color.WHITE
        private val INACTIVE = Color(0.55f, 0.57f, 0.60f)
    }
}
