package tuver.sudoku.di.impl

import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import tuver.sudoku.di.DomainModule
import tuver.sudoku.domain.ExtractBoardImageUseCase
import tuver.sudoku.domain.ExtractSudokuBoardUseCase
import tuver.sudoku.domain.GenerateSolutionImageUseCase
import tuver.sudoku.domain.SolveSudokuBoardUseCase
import tuver.sudoku.domain.impl.DefaultGenerateSolutionImageUseCase
import tuver.sudoku.domain.impl.DefaultSolveSudokuBoardUseCase
import tuver.sudoku.domain.impl.mlkit.MlKitExtractSudokuBoardUseCase
import tuver.sudoku.domain.impl.opencv.OpenCvExtractBoardImageUseCase
import tuver.sudoku.domain.impl.sudokusolver.BruteForceSudokuSolver

class DefaultDomainModule : DomainModule {

    private val textRecognizer: TextRecognizer
        get() = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    override val extractBoardImageUseCase: ExtractBoardImageUseCase by lazy {
        OpenCvExtractBoardImageUseCase()
    }

    override val extractSudokuBoardUseCase: ExtractSudokuBoardUseCase by lazy {
        MlKitExtractSudokuBoardUseCase(textRecognizer)
    }

    override val solveSudokuBoardUseCase: SolveSudokuBoardUseCase by lazy {
        DefaultSolveSudokuBoardUseCase { BruteForceSudokuSolver() }
    }

    override val generateSolutionImageUseCase: GenerateSolutionImageUseCase by lazy {
        DefaultGenerateSolutionImageUseCase()
    }

}