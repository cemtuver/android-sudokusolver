package tuver.sudokusolver.domain.impl.mlkit

import android.graphics.Bitmap
import com.google.mlkit.vision.text.TextRecognizer
import tuver.sudokusolver.domain.ExtractSudokuBoardUseCase
import tuver.sudokusolver.model.SudokuBoard
import tuver.sudokusolver.model.SudokuBoard.Companion.SUDOKU_BOARD_SIZE
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MlKitExtractSudokuBoardUseCase(
    private val textRecognizer: TextRecognizer
) : ExtractSudokuBoardUseCase {

    private fun generateCellImageList(boardImage: Bitmap): List<List<Bitmap>> {
        val cellSize = boardImage.width / SUDOKU_BOARD_SIZE

        return (0 until SUDOKU_BOARD_SIZE).map { i ->
            (0 until SUDOKU_BOARD_SIZE).map { j ->
                Bitmap.createBitmap(
                    boardImage,
                    cellSize * j,
                    cellSize * i,
                    cellSize,
                    cellSize
                )
            }
        }
    }

    override suspend fun extractSudokuBoard(boardImage: Bitmap): SudokuBoard = suspendCoroutine { continuation ->
        val sudokuBoardValuesSize = SUDOKU_BOARD_SIZE * SUDOKU_BOARD_SIZE
        val sudokuBoardValues = Array(SUDOKU_BOARD_SIZE) { IntArray(SUDOKU_BOARD_SIZE) { 0 } }
        val processedCellCount = AtomicInteger(0)
        val cellImageList = generateCellImageList(boardImage)

        for (i in 0 until SUDOKU_BOARD_SIZE) {
            for (j in 0 until SUDOKU_BOARD_SIZE) {
                textRecognizer.process(cellImageList[i][j], 0).addOnSuccessListener { result ->
                    result.text.toIntOrNull()?.let { sudokuBoardValues[i][j] = it }

                    if (processedCellCount.addAndGet(1) == sudokuBoardValuesSize) {
                        continuation.resume(sudokuBoardValues.toSudokuBoard())
                    }
                }
            }
        }
    }

    companion object {

        private fun Array<IntArray>.toSudokuBoard(): SudokuBoard {
            return SudokuBoard(toList().map { it.toList() })
        }

    }

}