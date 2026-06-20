package com.neoutils.skiko

import com.neoutils.core.input.Key
import java.awt.event.KeyEvent

internal fun keyOf(awtKeyCode: Int): Key = when (awtKeyCode) {
    KeyEvent.VK_LEFT -> Key.LEFT
    KeyEvent.VK_RIGHT -> Key.RIGHT
    KeyEvent.VK_UP -> Key.UP
    KeyEvent.VK_DOWN -> Key.DOWN
    KeyEvent.VK_SPACE -> Key.SPACE
    KeyEvent.VK_ENTER -> Key.ENTER
    KeyEvent.VK_ESCAPE -> Key.ESCAPE
    KeyEvent.VK_A -> Key.A
    KeyEvent.VK_B -> Key.B
    KeyEvent.VK_C -> Key.C
    KeyEvent.VK_D -> Key.D
    KeyEvent.VK_E -> Key.E
    KeyEvent.VK_F -> Key.F
    KeyEvent.VK_G -> Key.G
    KeyEvent.VK_H -> Key.H
    KeyEvent.VK_I -> Key.I
    KeyEvent.VK_J -> Key.J
    KeyEvent.VK_K -> Key.K
    KeyEvent.VK_L -> Key.L
    KeyEvent.VK_M -> Key.M
    KeyEvent.VK_N -> Key.N
    KeyEvent.VK_O -> Key.O
    KeyEvent.VK_P -> Key.P
    KeyEvent.VK_Q -> Key.Q
    KeyEvent.VK_R -> Key.R
    KeyEvent.VK_S -> Key.S
    KeyEvent.VK_T -> Key.T
    KeyEvent.VK_U -> Key.U
    KeyEvent.VK_V -> Key.V
    KeyEvent.VK_W -> Key.W
    KeyEvent.VK_X -> Key.X
    KeyEvent.VK_Y -> Key.Y
    KeyEvent.VK_Z -> Key.Z
    KeyEvent.VK_0 -> Key.DIGIT_0
    KeyEvent.VK_1 -> Key.DIGIT_1
    KeyEvent.VK_2 -> Key.DIGIT_2
    KeyEvent.VK_3 -> Key.DIGIT_3
    KeyEvent.VK_4 -> Key.DIGIT_4
    KeyEvent.VK_5 -> Key.DIGIT_5
    KeyEvent.VK_6 -> Key.DIGIT_6
    KeyEvent.VK_7 -> Key.DIGIT_7
    KeyEvent.VK_8 -> Key.DIGIT_8
    KeyEvent.VK_9 -> Key.DIGIT_9
    else -> Key.UNKNOWN
}
