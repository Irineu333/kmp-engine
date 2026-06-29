package com.neoutils.example

import com.neoutils.core.node.Label

class CenterAlignLabel : Label() {

    override fun onProcess(delta: Float) {
        position = -getSize().center()
    }
}
