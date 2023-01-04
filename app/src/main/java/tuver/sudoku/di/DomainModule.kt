package tuver.sudoku.di

import tuver.sudoku.domain.ExtractBoardImageUseCase
import tuver.sudoku.domain.ExtractSudokuBoardUseCase
import tuver.sudoku.domain.GenerateSolutionImageUseCase
import tuver.sudoku.domain.SolveSudokuBoardUseCase

interface DomainModule {

    val extractBoardImageUseCase: ExtractBoardImageUseCase

    val extractSudokuBoardUseCase: ExtractSudokuBoardUseCase

    val solveSudokuBoardUseCase: SolveSudokuBoardUseCase

    val generateSolutionImageUseCase: GenerateSolutionImageUseCase

}