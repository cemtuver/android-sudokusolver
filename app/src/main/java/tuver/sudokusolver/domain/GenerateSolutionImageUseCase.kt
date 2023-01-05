package tuver.sudokusolver.domain

import android.graphics.Bitmap
import tuver.sudokusolver.model.SudokuBoard

interface GenerateSolutionImageUseCase {

    suspend fun generateSolutionImage(
        boardImage: Bitmap,
        sudokuBoard: SudokuBoard,
        solutionSudokuBoard: SudokuBoard
    ): Bitmap

}