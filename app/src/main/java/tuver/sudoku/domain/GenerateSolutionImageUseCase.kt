package tuver.sudoku.domain

import android.graphics.Bitmap
import tuver.sudoku.model.SudokuBoard

interface GenerateSolutionImageUseCase {

    suspend fun generateSolutionImage(
        boardImage: Bitmap,
        sudokuBoard: SudokuBoard,
        solutionSudokuBoard: SudokuBoard
    ): Bitmap

}