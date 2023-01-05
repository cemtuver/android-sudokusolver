package tuver.sudokusolver.domain

import tuver.sudokusolver.model.SudokuBoard

interface SolveSudokuBoardUseCase {

    suspend fun solveSudokuBoard(sudokuBoard: SudokuBoard): SudokuBoard

}