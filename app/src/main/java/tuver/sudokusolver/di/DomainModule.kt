package tuver.sudokusolver.di

import tuver.sudokusolver.domain.ExtractBoardImageUseCase
import tuver.sudokusolver.domain.ExtractSudokuBoardUseCase
import tuver.sudokusolver.domain.GenerateSolutionImageUseCase
import tuver.sudokusolver.domain.SolveSudokuBoardUseCase

interface DomainModule {

    val extractBoardImageUseCase: ExtractBoardImageUseCase

    val extractSudokuBoardUseCase: ExtractSudokuBoardUseCase

    val solveSudokuBoardUseCase: SolveSudokuBoardUseCase

    val generateSolutionImageUseCase: GenerateSolutionImageUseCase

}