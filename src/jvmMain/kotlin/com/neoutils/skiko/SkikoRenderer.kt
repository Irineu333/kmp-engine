package com.neoutils.skiko

import com.neoutils.core.Color
import com.neoutils.core.Renderer
import com.neoutils.core.Size
import com.neoutils.core.Vec2
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Font
import org.jetbrains.skia.FontMgr
import org.jetbrains.skia.FontStyle
import org.jetbrains.skia.Paint

private val typeface = FontMgr.default.matchFamilyStyleCharacter(
    familyName = null,
    style = FontStyle.NORMAL,
    bcp47 = null,
    character = 'A'.code,
)

class SkikoRenderer : Renderer {

    var canvas: Canvas? = null

    override fun measureText(
        text: String,
        size: Float
    ): Size {
        val font = getFontFor(size)
        val metrics = font.metrics
        return Size(
            width = font.measureTextWidth(text),
            height = metrics.descent - metrics.ascent,
        )
    }

    override fun drawText(
        text: String,
        position: Vec2,
        size: Float,
        color: Color
    ) {
        val canvas = canvas ?: return

        val paint = getPaintFor(color)

        val font = getFontFor(size)

        val baseline = position.y - font.metrics.ascent

        canvas.drawString(text, position.x, baseline, font, paint)
    }

    private fun getPaintFor(color: Color): Paint {
        return Paint().apply {
            this.color = color.toSkiaArgb()
            isAntiAlias = true
        }
    }

    private fun getFontFor(size: Float): Font {
        return Font(typeface, size)
    }

    private fun Color.toSkiaArgb(): Int {
        val a = (a.coerceIn(0f, 1f) * 255f + 0.5f).toInt() and 0xFF
        val r = (r.coerceIn(0f, 1f) * 255f + 0.5f).toInt() and 0xFF
        val g = (g.coerceIn(0f, 1f) * 255f + 0.5f).toInt() and 0xFF
        val b = (b.coerceIn(0f, 1f) * 255f + 0.5f).toInt() and 0xFF
        return (a shl 24) or (r shl 16) or (g shl 8) or b
    }
}