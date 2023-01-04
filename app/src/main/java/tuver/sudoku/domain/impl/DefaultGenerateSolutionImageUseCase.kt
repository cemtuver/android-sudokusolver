package tuver.sudoku.domain.impl

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import tuver.sudoku.domain.GenerateSolutionImageUseCase
import tuver.sudoku.model.SudokuBoard
import tuver.sudoku.model.SudokuBoard.Companion.SUDOKU_BOARD_SIZE

class DefaultGenerateSolutionImageUseCase : GenerateSolutionImageUseCase {

    override suspend fun generateSolutionImage(
        boardImage: Bitmap,
        sudokuBoard: SudokuBoard,
        solutionSudokuBoard: SudokuBoard
    ): Bitmap {
        val canvas = Canvas(boardImage)
        val cellSize = boardImage.width / SUDOKU_BOARD_SIZE
        val paint = Paint().apply {
            color = Color.GREEN
            textSize = 65f
        }

        for (i in 0 until SUDOKU_BOARD_SIZE) {
            for (j in 0 until SUDOKU_BOARD_SIZE) {
                if (sudokuBoard.values[i][j] != 0) continue

                canvas.drawText(
                    solutionSudokuBoard.values[i][j].toString(),
                    j * cellSize + 40f,
                    i * cellSize + 85f,
                    paint
                )
            }
        }

        return boardImage
    }

}