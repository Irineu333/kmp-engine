package com.neoutils.skiko

import com.neoutils.core.input.InputQueue

/** Platform-specific [InputQueue] used to bridge host input events into the engine. */
expect fun createInputQueue(): InputQueue
