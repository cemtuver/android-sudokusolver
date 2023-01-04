package tuver.sudoku.domain.impl

import tuver.sudoku.domain.SolveSudokuBoardUseCase
import tuver.sudoku.domain.impl.sudokusolver.SudokuSolver
import tuver.sudoku.model.SudokuBoard
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class DefaultSolveSudokuBoardUseCase(
    private val sudokuSolverFactory: () -> SudokuSolver
) : SolveSudokuBoardUseCase {

    override suspend fun solveSudokuBoard(sudokuBoard: SudokuBoard): SudokuBoard = suspendCoroutine { continuation ->
        val sudokuSolver = sudokuSolverFactory()
        val solutionBoard = sudokuSolver.solve(sudokuBoard)

        continuation.resume(solutionBoard)
    }

}