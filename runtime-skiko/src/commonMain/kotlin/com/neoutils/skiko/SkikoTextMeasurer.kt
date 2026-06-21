package com.neoutils.skiko

import com.neoutils.core.math.Size
import com.neoutils.core.graphics.TextMeasurer

class SkikoTextMeasurer : TextMeasurer {

    override fun measure(
        text: String,
        size: Float
    ): Size {
        val font = fontFor(size)
        val metrics = font.metrics
        return Size(
            width = font.measureTextWidth(text),
            height = metrics.descent - metrics.ascent,
        )
    }
}
