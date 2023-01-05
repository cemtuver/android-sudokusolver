package tuver.sudokusolver.domain.impl.opencv

import android.graphics.Bitmap
import org.opencv.android.Utils.bitmapToMat
import org.opencv.android.Utils.matToBitmap
import org.opencv.core.*
import org.opencv.imgproc.Imgproc.*
import tuver.sudokusolver.domain.ExtractBoardImageUseCase
import tuver.sudokusolver.model.SudokuBoard.Companion.SUDOKU_BOARD_SIZE
import tuver.sudokusolver.util.extension.toMutable
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class OpenCvExtractBoardImageUseCase : ExtractBoardImageUseCase {

    private fun prepareProcessingMat(originalMat: Mat, processingMat: Mat) {
        cvtColor(originalMat, processingMat, COLOR_RGB2GRAY)
        // TODO: Bilateral filtering might perform better
        GaussianBlur(processingMat, processingMat, Size(11.0, 11.0), 0.0)
        adaptiveThreshold(processingMat, processingMat, 255.0, ADAPTIVE_THRESH_MEAN_C, THRESH_BINARY_INV, 5, 2.0);
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

    private fun findCornerListOfContour(largestContour: MatOfPoint): List<Point> {
        val src = MatOfPoint2f()
        val approxPoly = MatOfPoint2f()
        largestContour.convertTo(src, CvType.CV_32FC2)
        val arcLength = arcLength(src, true)
        approxPolyDP(src, approxPoly, 0.02 * arcLength, true)

        return approxPoly.toList()
    }

    private fun createPerspectiveMat(originalMat: Mat, largestContourCornerList: List<Point>): Mat {
        val perspectiveMat = Mat()
        val perspectiveSrc = largestContourCornerList.run { MatOfPoint2f(topLeft, topRight, bottomLeft, bottomRight) }
        val perspectiveDst = MatOfPoint2f(Point(0.0, 0.0), Point(BOARD_SIZE_IN_PX, 0.0), Point(0.0, BOARD_SIZE_IN_PX), Point(BOARD_SIZE_IN_PX, BOARD_SIZE_IN_PX))
        val perspectiveTransform = getPerspectiveTransform(perspectiveSrc, perspectiveDst)

        warpPerspective(
            originalMat,
            perspectiveMat,
            perspectiveTransform,
            Size(BOARD_SIZE_IN_PX, BOARD_SIZE_IN_PX)
        )

        return perspectiveMat
    }

    private fun createBoardImage(perspectiveMat: Mat): Bitmap {
        val boardImage = Bitmap.createBitmap(
            perspectiveMat.cols(),
            perspectiveMat.rows(),
            Bitmap.Config.ARGB_8888
        )

        matToBitmap(perspectiveMat, boardImage)
        return boardImage
    }

    override suspend fun extractBoardImage(image: Bitmap): Bitmap = suspendCoroutine { continuation ->
        val mutableBitmap = image.toMutable()
        val originalMat = Mat(mutableBitmap.height, mutableBitmap.width, CvType.CV_8UC1).also {
            bitmapToMat(mutableBitmap, it)
        }

        val processingMat = Mat(mutableBitmap.height, mutableBitmap.width, CvType.CV_8UC1).also {
            prepareProcessingMat(originalMat, it)
        }

        val largestContour = findLargestContour(processingMat)
        val largestContourCornerList = findCornerListOfContour(largestContour)
        val perspectiveMat = createPerspectiveMat(originalMat, largestContourCornerList)
        val boardImage = createBoardImage(perspectiveMat)

        mutableBitmap.recycle()
        continuation.resume(boardImage)
    }

    companion object {

        private const val BOARD_SIZE_IN_PX = SUDOKU_BOARD_SIZE * 111.0

        private val List<Point>.topLeft: Point
            get() = sortedBy { it.x }.take(2).minBy { it.y }

        private val List<Point>.bottomLeft: Point
            get() = sortedBy { it.x }.take(2).maxBy { it.y }

        private val List<Point>.topRight: Point
            get() = sortedByDescending { it.x }.take(2).minBy { it.y }

        private val List<Point>.bottomRight: Point
            get() = sortedByDescending { it.x }.take(2).maxBy { it.y }

    }

}