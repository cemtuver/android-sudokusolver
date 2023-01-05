package tuver.sudokusolver.util.extension

import android.graphics.Bitmap

fun Bitmap.toMutable(config: Bitmap.Config = Bitmap.Config.ARGB_8888): Bitmap {
    return copy(config, true).also {
        if (this != it) {
            this.recycle()
        }
    }
}