package com.neoutils.core.math

data class Size(
    val width: Float,
    val height: Float,
) {

    /** This size's half-extent (the center of a box of this size at the origin). */
    fun center(): Vec2 = Vec2(width / 2f, height / 2f)

    /** Top-left position that centers [content] within this size. */
    fun center(content: Size): Vec2 = center() - content.center()

    companion object {
        val ZERO: Size = Size(0f, 0f)
    }
}
