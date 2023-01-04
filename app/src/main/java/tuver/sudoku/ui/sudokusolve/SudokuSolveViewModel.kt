package tuver.sudoku.ui.sudokusolve

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tuver.sudoku.domain.ExtractBoardImageUseCase
import tuver.sudoku.domain.ExtractSudokuBoardUseCase
import tuver.sudoku.domain.GenerateSolutionImageUseCase
import tuver.sudoku.domain.SolveSudokuBoardUseCase
import tuver.sudoku.util.SingleLiveEvent

class SudokuSolveViewModel(
    private val extractBoardImageUseCase: ExtractBoardImageUseCase,
    private val extractSudokuBoardUseCase: ExtractSudokuBoardUseCase,
    private val solveSudokuBoardUseCase: SolveSudokuBoardUseCase,
    private val generateSolutionImageUseCase: GenerateSolutionImageUseCase
) : ViewModel() {

    private val viewState = MutableLiveData<SudokuSolveViewState>()

    private val viewAction = SingleLiveEvent<SudokuSolveViewAction>()

    fun viewState(): LiveData<SudokuSolveViewState> {
        return viewState
    }

    fun viewAction(): LiveData<SudokuSolveViewAction> {
        return viewAction
    }

    fun onPickImageButtonClick() {
        viewAction.postValue(SudokuSolveViewAction.LaunchImagePick)
    }

    fun onImagePick(image: Bitmap) {
        viewState.postValue(SudokuSolveViewState.Loading)
        viewModelScope.launch {
            val boardImage = extractBoardImageUseCase.extractBoardImage(image)
            val sudokuBoard = extractSudokuBoardUseCase.extractSudokuBoard(boardImage)
            val solutionSudokuBoard = solveSudokuBoardUseCase.solveSudokuBoard(sudokuBoard)
            val solutionImage = generateSolutionImageUseCase.generateSolutionImage(boardImage, sudokuBoard, solutionSudokuBoard)

            viewState.postValue(SudokuSolveViewState.Success(solutionImage))
        }
    }

}