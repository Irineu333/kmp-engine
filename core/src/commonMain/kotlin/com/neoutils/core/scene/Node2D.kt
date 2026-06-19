package com.neoutils.core.scene

import com.neoutils.core.math.Rect
import com.neoutils.core.math.Vec2

open class Node2D : Node() {

    var position: Vec2 = Vec2.ZERO

    open fun bounds(): Rect? = null

    fun globalPosition(): Vec2 {

        val parent = parent

        if (parent is Node2D) {
            return position + parent.globalPosition()
        }

        return position
    }

    fun globalBounds(): Rect? {

        val local = bounds() ?: return null

        return Rect(globalPosition() + local.position, local.size)
    }
}
