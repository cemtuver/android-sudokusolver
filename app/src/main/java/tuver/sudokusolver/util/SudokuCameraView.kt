package tuver.sudokusolver.util

import android.content.Context
import android.graphics.Bitmap
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.util.AttributeSet
import android.view.SurfaceView
import org.opencv.android.CameraBridgeViewBase
import org.opencv.android.JavaCamera2View
import org.opencv.core.Core.*
import org.opencv.core.Mat
import tuver.sudokusolver.util.extension.calculateRelativeOrientation

class SudokuCameraView : JavaCamera2View, CameraBridgeViewBase.CvCameraViewListener2 {

    interface SudokuDetectListener {

        fun onSudokuDetected(bitmap: Bitmap)

    }

    private var sudokuDetectListener: SudokuDetectListener? = null

    private var displayRotationInDegree: Int = 0

    private val cameraManager: CameraManager by lazy {
        context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    }

    private val cameraCharacteristics: CameraCharacteristics by lazy {
        cameraManager.getCameraCharacteristics(mCameraID)
    }

    constructor(context: Context, cameraId: Int) : super(context, cameraId)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    fun setSudokuDetectListener(sudokuDetectListener: SudokuDetectListener) {
        this.sudokuDetectListener = sudokuDetectListener
    }

    fun start(displayRotationInDegree: Int) {
        this.displayRotationInDegree = displayRotationInDegree
        setCvCameraViewListener(this)
        setCameraPermissionGranted()
        setCameraIndex(CameraBridgeViewBase.CAMERA_ID_BACK)
        enableView()
        visibility = SurfaceView.VISIBLE
    }

    override fun onCameraViewStarted(width: Int, height: Int) {
        // NOP
    }

    override fun onCameraViewStopped() {
        // NOP
    }

    override fun onCameraFrame(inputFrame: CvCameraViewFrame): Mat {
        val rgbaMat = inputFrame.rgba()
        val relativeSensorOrientation = cameraCharacteristics.calculateRelativeOrientation(displayRotationInDegree)
        val matRotation = when (relativeSensorOrientation) {
            90 -> ROTATE_90_CLOCKWISE
            180 -> ROTATE_180
            270 -> ROTATE_90_COUNTERCLOCKWISE
            else -> null
        }

        matRotation?.let { rotate(rgbaMat, rgbaMat, it) }

        return rgbaMat
    }

}