package tuver.sudokusolver.util.extension

import android.view.Display
import android.view.Surface

val Display.rotationInDegree: Int
    get() = when (rotation) {
        Surface.ROTATION_90 -> 90
        Surface.ROTATION_180 -> 180
        Surface.ROTATION_270 -> 270
        else -> 0
    }