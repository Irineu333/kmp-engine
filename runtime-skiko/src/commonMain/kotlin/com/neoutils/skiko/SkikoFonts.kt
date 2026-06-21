package com.neoutils.skiko

import org.jetbrains.skia.Font
import org.jetbrains.skia.Typeface

internal var typeface: Typeface? = defaultTypeface()
    private set

// Bounded cache: a few distinct sizes at most, so it never grows unbounded.
// Assumes single-threaded access from the Skiko render thread.
private val fonts = mutableMapOf<Float, Font>()

// Per-platform default typeface: the JVM resolves a system font; wasmJs has no system
// fonts and returns null, then installs one asynchronously via installTypeface() before
// the first frame (see SkikoFonts.wasmJs.kt + the wasm SkikoCanvas).
internal expect fun defaultTypeface(): Typeface?

internal fun installTypeface(value: Typeface?) {
    typeface = value
    fonts.clear() // cached Fonts hold the previous typeface
}

internal fun fontFor(size: Float): Font {
    return fonts.getOrPut(size) { Font(typeface, size) }
}
