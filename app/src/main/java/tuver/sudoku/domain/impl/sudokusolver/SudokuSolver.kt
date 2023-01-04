package tuver.sudoku.domain.impl.sudokusolver

import tuver.sudoku.model.SudokuBoard

interface SudokuSolver {

    fun solve(sudokuBoard: SudokuBoard): SudokuBoard

}