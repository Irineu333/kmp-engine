package com.neoutils

import org.jetbrains.skia.Canvas
import org.jetbrains.skia.Color
import org.jetbrains.skia.Font
import org.jetbrains.skia.FontMgr
import org.jetbrains.skia.FontStyle
import org.jetbrains.skia.Paint
import org.jetbrains.skiko.SkikoRenderDelegate

/** Draws a centered "Hello, World!" on the Skia canvas. */
class HelloWorldScene(
    private val text: String = "Hello, World!",
) : SkikoRenderDelegate {

    // Paint and Font are native resources: create them once and reuse every frame.
    private val paint = Paint().apply {
        color = Color.makeRGB(30, 144, 255) // dodger blue
        isAntiAlias = true
    }

    // Resolve a typeface via the system's font fallback. matchFamilyStyle(null, ...)
    // returns null on most platforms (there's no "default system family"), yielding a
    // glyphless font and a blank render. matchFamilyStyleCharacter asks the OS for a
    // font that can actually draw a given glyph, so it returns a usable typeface.
    private val typeface = FontMgr.default.matchFamilyStyleCharacter(
        familyName = null,
        style = FontStyle.NORMAL,
        bcp47 = null,
        character = 'A'.code,
    )
    private val font = Font(typeface, 48f)

    override fun onRender(canvas: Canvas, width: Int, height: Int, nanoTime: Long) {
        canvas.clear(Color.WHITE)

        // Center the text on screen.
        val textWidth = font.measureTextWidth(text, paint)
        val x = (width - textWidth) / 2f
        val y = height / 2f
        canvas.drawString(text, x, y, font, paint)
    }
}
