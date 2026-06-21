package com.neoutils.skiko

import org.jetbrains.skia.FontMgr
import org.jetbrains.skia.FontStyle
import org.jetbrains.skia.Typeface

// The JVM has system fonts: pick one that can render a basic Latin glyph.
internal actual fun defaultTypeface(): Typeface? = FontMgr.default.matchFamilyStyleCharacter(
    familyName = null,
    style = FontStyle.NORMAL,
    bcp47 = null,
    character = 'A'.code,
)
