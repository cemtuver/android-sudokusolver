package tuver.sudoku.ui.sudokusolve

import android.graphics.Bitmap

sealed class SudokuSolveViewState {
    object Loading : SudokuSolveViewState()
    class Success(val solutionImage: Bitmap) : SudokuSolveViewState()
}