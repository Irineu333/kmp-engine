package com.neoutils.core.graphics

import com.neoutils.core.math.Size

/**
 * Render context provided by the host each frame: the drawable [size] and the
 * [textMeasurer] used to lay out text. Persists across scene changes.
 */
class Viewport {
    var size: Size = Size.ZERO
    var textMeasurer: TextMeasurer? = null
}
