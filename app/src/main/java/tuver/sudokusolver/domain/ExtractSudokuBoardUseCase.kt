package tuver.sudokusolver.domain

import android.graphics.Bitmap
import tuver.sudokusolver.model.SudokuBoard

interface ExtractSudokuBoardUseCase {

    suspend fun extractSudokuBoard(boardImage: Bitmap): SudokuBoard

}