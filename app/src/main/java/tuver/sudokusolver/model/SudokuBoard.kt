package tuver.sudokusolver.model

data class SudokuBoard(
    val values: List<List<Int>>
) {

    companion object {

        const val SUDOKU_BOARD_SIZE = 9

    }

}