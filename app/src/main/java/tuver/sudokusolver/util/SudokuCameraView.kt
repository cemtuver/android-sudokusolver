package tuver.sudokusolver.util

import android.content.Context
import android.util.AttributeSet
import org.opencv.android.JavaCamera2View

class SudokuCameraView : JavaCamera2View {

    constructor(context: Context, cameraId: Int) : super(context, cameraId)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

}