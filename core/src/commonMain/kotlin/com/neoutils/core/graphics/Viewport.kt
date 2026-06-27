package com.neoutils.core.graphics

import com.neoutils.core.math.Size

/** Global, runtime-provided presentation services shared by every scene. */
object Viewport {
    var size: Size = Size.ZERO
    var textMeasurer: TextMeasurer? = null
}
