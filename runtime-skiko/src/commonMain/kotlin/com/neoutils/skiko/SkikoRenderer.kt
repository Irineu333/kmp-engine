package com.neoutils.skiko

import com.neoutils.core.graphics.Color
import com.neoutils.core.math.Rect
import com.neoutils.core.graphics.Renderer
import com.neoutils.core.math.Vec2
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Paint
import org.jetbrains.skia.PaintMode

class SkikoRenderer : Renderer {

    var canvas: Canvas? = null

    // Single reusable Paint: native Skia objects are not GC-managed, so allocating
    // one per draw call leaked native memory. Reused on the render thread only.
    private val paint = Paint().apply { isAntiAlias = true }

    override fun drawText(
        text: String,
        position: Vec2,
        size: Float,
        color: Color
    ) {
        val canvas = canvas ?: return

        val paint = paint.apply {
            this.color = color.toSkiaArgb()
            mode = PaintMode.FILL
        }

        val font = fontFor(size)

        val baseline = position.y - font.metrics.ascent

        canvas.drawString(text, position.x, baseline, font, paint)
    }

    override fun drawRect(
        rect: Rect,
        color: Color,
        fill: Boolean
    ) {
        val canvas = canvas ?: return

        val paint = paint.apply {
            this.color = color.toSkiaArgb()
            mode = if (fill) PaintMode.FILL else PaintMode.STROKE
            strokeWidth = 1f
        }

        canvas.drawRect(
            org.jetbrains.skia.Rect.makeXYWH(
                rect.position.x,
                rect.position.y,
                rect.size.width,
                rect.size.height,
            ),
            paint,
        )
    }

    override fun drawCircle(
        center: Vec2,
        radius: Float,
        color: Color,
        fill: Boolean
    ) {
        val canvas = canvas ?: return

        val paint = paint.apply {
            this.color = color.toSkiaArgb()
            mode = if (fill) PaintMode.FILL else PaintMode.STROKE
            strokeWidth = 1f
        }

        canvas.drawCircle(center.x, center.y, radius, paint)
    }

    override fun drawLine(
        start: Vec2,
        end: Vec2,
        color: Color,
        width: Float
    ) {
        val canvas = canvas ?: return

        val paint = paint.apply {
            this.color = color.toSkiaArgb()
            mode = PaintMode.STROKE
            strokeWidth = width
        }

        canvas.drawLine(start.x, start.y, end.x, end.y, paint)
    }

    private fun Color.toSkiaArgb(): Int {
        val a = (a.coerceIn(0f, 1f) * 255f + 0.5f).toInt() and 0xFF
        val r = (r.coerceIn(0f, 1f) * 255f + 0.5f).toInt() and 0xFF
        val g = (g.coerceIn(0f, 1f) * 255f + 0.5f).toInt() and 0xFF
        val b = (b.coerceIn(0f, 1f) * 255f + 0.5f).toInt() and 0xFF
        return (a shl 24) or (r shl 16) or (g shl 8) or b
    }
}