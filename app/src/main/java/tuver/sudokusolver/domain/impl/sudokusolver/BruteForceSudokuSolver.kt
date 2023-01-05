package tuver.sudokusolver.domain.impl.sudokusolver

import tuver.sudokusolver.model.SudokuBoard
import tuver.sudokusolver.model.SudokuBoard.Companion.SUDOKU_BOARD_SIZE

class BruteForceSudokuSolver : SudokuSolver {

    private var board: Array<IntArray> = Array(SUDOKU_BOARD_SIZE) { IntArray(SUDOKU_BOARD_SIZE) { 0 } }
    private val rowMaps = Array(SUDOKU_BOARD_SIZE) { mutableSetOf<Int>() }
    private val columnMaps = Array(SUDOKU_BOARD_SIZE) { mutableSetOf<Int>() }
    private val squareMaps = Array(SUDOKU_BOARD_SIZE) { mutableSetOf<Int>() }
    private val emptyCells = mutableListOf<Pair<Int, Int>>()

    private fun setupCells() {
        for (row in 0 until SUDOKU_BOARD_SIZE) {
            for (column in 0 until SUDOKU_BOARD_SIZE) {
                when (board[row][column]) {
                    0 -> emptyCells.add(Pair(row, column))
                    else -> fill(row, column, board[row][column])
                }
            }
        }
    }

    private fun getSquareIndex(row: Int, column: Int): Int {
        val squareCount = SUDOKU_BOARD_SIZE / 3
        return (row / squareCount) * squareCount + (column / squareCount)
    }

    private fun canFill(row: Int, column: Int, value: Int): Boolean {
        return !rowMaps[row].contains(value) &&
                !columnMaps[column].contains(value) &&
                !squareMaps[getSquareIndex(row, column)].contains(value)
    }

    private fun fill(row: Int, column: Int, value: Int) {
        board[row][column] = value
        rowMaps[row].add(value)
        columnMaps[column].add(value)
        squareMaps[getSquareIndex(row, column)].add(value)
    }

    private fun clear(row: Int, column: Int, value: Int) {
        board[row][column] = 0
        rowMaps[row].remove(value)
        columnMaps[column].remove(value)
        squareMaps[getSquareIndex(row, column)].remove(value)
    }

    private fun solveBoard(iteration: Int): Boolean {
        if (iteration == emptyCells.size) return true
        val (row, column) = emptyCells[iteration]

        for (number in 1..9) {
            if (!canFill(row, column, number)) continue
            fill(row, column, number)

            if (solveBoard(iteration + 1)) {
                return true
            }

            clear(row, column, number)
        }

        return false
    }

    override fun solve(sudokuBoard: SudokuBoard): SudokuBoard {
        sudokuBoard.values.forEachIndexed { row, columnList ->
            columnList.forEachIndexed { column, item ->
                board[row][column] = item
            }
        }

        setupCells()
        solveBoard(0)

        return board.toSudokuGame()
    }

    companion object {

        private fun Array<IntArray>.toSudokuGame(): SudokuBoard {
            return SudokuBoard(toList().map { it.toList() })
        }

    }

}