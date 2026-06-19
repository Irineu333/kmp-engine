package com.neoutils.core.graphics

import com.neoutils.core.math.Size

interface TextMeasurer {
    fun measure(
        text: String,
        size: Float
    ): Size
}
