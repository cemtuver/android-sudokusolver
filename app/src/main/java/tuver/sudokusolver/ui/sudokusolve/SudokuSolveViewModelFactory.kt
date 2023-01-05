package tuver.sudokusolver.ui.sudokusolve

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tuver.sudokusolver.di.AppModule

class SudokuSolveViewModelFactory(
    private val appModule: AppModule
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SudokuSolveViewModel(
            appModule.extractBoardImageUseCase,
            appModule.extractSudokuBoardUseCase,
            appModule.solveSudokuBoardUseCase,
            appModule.generateSolutionImageUseCase
        ) as T
    }

}