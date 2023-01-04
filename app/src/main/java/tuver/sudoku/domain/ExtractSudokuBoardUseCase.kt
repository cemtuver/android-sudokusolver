package tuver.sudoku.domain

import android.graphics.Bitmap
import tuver.sudoku.model.SudokuBoard

interface ExtractSudokuBoardUseCase {

    suspend fun extractSudokuBoard(boardImage: Bitmap): SudokuBoard

}