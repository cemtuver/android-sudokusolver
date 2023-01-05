package tuver.sudokusolver.domain.impl.sudokusolver

import tuver.sudokusolver.model.SudokuBoard

interface SudokuSolver {

    fun solve(sudokuBoard: SudokuBoard): SudokuBoard

}