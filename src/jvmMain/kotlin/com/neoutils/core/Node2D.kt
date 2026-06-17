package com.neoutils.core

open class Node2D : Node() {

    var position: Vec2 = Vec2.ZERO

    fun globalPosition(): Vec2 {

        val parent = parent

        if (parent is Node2D) {
            return position + parent.globalPosition()
        }

        return position
    }
}