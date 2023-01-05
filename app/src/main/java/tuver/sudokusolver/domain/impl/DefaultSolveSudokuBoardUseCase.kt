package tuver.sudokusolver.domain.impl

import tuver.sudokusolver.domain.SolveSudokuBoardUseCase
import tuver.sudokusolver.domain.impl.sudokusolver.SudokuSolver
import tuver.sudokusolver.model.SudokuBoard
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