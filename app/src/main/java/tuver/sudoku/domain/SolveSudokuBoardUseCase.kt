package tuver.sudoku.domain

import tuver.sudoku.model.SudokuBoard

interface SolveSudokuBoardUseCase {

    suspend fun solveSudokuBoard(sudokuBoard: SudokuBoard): SudokuBoard

}