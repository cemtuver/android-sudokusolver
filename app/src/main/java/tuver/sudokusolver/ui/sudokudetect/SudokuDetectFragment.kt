package tuver.sudokusolver.ui.sudokudetect

import android.Manifest
import android.content.pm.PackageManager
import android.hardware.camera2.CameraManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import org.opencv.android.CameraBridgeViewBase
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2
import org.opencv.core.Core.*
import org.opencv.core.Mat
import org.opencv.core.MatOfPoint
import org.opencv.core.Scalar
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import org.opencv.imgproc.Imgproc.*
import tuver.sudokusolver.R
import tuver.sudokusolver.databinding.FragmentSudokuDetectBinding

class SudokuDetectFragment : Fragment(R.layout.fragment_sudoku_detect), CvCameraViewListener2 {

    private var bindingField: FragmentSudokuDetectBinding? = null

    private val binding: FragmentSudokuDetectBinding
        get() = bindingField ?: throw IllegalAccessException("Binding is not initialized or valid")

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            onCameraPermissionGranted()
        }
    }

    private fun onCameraPermissionGranted() {

        binding.cameraView.apply {
            setCvCameraViewListener(this@SudokuDetectFragment)
            setCameraPermissionGranted()
            setCameraIndex(CameraBridgeViewBase.CAMERA_ID_BACK)
            enableView()

            visibility = SurfaceView.VISIBLE
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)?.also {
            bindingField = FragmentSudokuDetectBinding.bind(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            onCameraPermissionGranted()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingField = null
    }

    override fun onCameraViewStarted(width: Int, height: Int) {

    }

    override fun onCameraViewStopped() {

    }

    private fun findLargestContour(processingMat: Mat): MatOfPoint {
        val hierarchy = Mat()
        val contourList = mutableListOf<MatOfPoint>()

        findContours(
            processingMat,
            contourList,
            hierarchy,
            RETR_TREE,
            CHAIN_APPROX_SIMPLE
        )

        return contourList.maxBy { contourArea(it) }
    }

    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame): Mat {
        val rgbaMat = inputFrame.rgba()
        val grayMat = inputFrame.gray()
        GaussianBlur(grayMat, grayMat, Size(11.0, 11.0), 0.0)
        adaptiveThreshold(grayMat, grayMat, 255.0, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV, 13, 2.0);
        val largestContour = findLargestContour(grayMat)

        drawContours(rgbaMat, mutableListOf(largestContour), 0, Scalar(255.0, 0.0, 255.0), 10)

        grayMat.release()

//        rotate(rgbaMat, rgbaMat, ROTATE_90_CLOCKWISE)

        return rgbaMat
    }

    companion object {

        fun newInstance(): SudokuDetectFragment {
            return SudokuDetectFragment()
        }

    }


}