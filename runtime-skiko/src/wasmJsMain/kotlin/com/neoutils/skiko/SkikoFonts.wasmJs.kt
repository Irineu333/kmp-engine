package com.neoutils.skiko

import kotlinx.browser.window
import kotlinx.coroutines.await
import org.jetbrains.skia.Data
import org.jetbrains.skia.FontMgr
import org.jetbrains.skia.Typeface
import org.khronos.webgl.ArrayBuffer
import org.khronos.webgl.Int8Array
import org.khronos.webgl.get
import org.w3c.fetch.Response
import kotlin.js.ExperimentalWasmJsInterop

// The browser exposes no system fonts to Skia, so there is no default typeface until
// loadDefaultFont() fetches a bundled .ttf and the caller installs it.
internal actual fun defaultTypeface(): Typeface? = null

/**
 * Fetches the bundled font over HTTP (it is served from the web root alongside the app's
 * JS), decodes it and returns it as a [Typeface]. This only loads the font; the caller
 * installs it (via [installTypeface]) before the first frame so text has glyphs.
 */
@OptIn(ExperimentalWasmJsInterop::class)
internal suspend fun loadDefaultFont(path: String = "font.ttf"): Typeface? {
    // The wasm await() is Promise<JsAny?>.await(): T, so its result type must be pinned by
    // the declared variable type rather than inferred from the chained call.
    val response: Response = window.fetch(path).await()
    val buffer: ArrayBuffer = response.arrayBuffer().await()
    val array = Int8Array(buffer)
    val bytes = ByteArray(array.length) { array[it] }
    // FontMgr.makeFromData decodes the bytes directly; it needs no system fonts.
    return FontMgr.default.makeFromData(Data.makeFromBytes(bytes))
}
