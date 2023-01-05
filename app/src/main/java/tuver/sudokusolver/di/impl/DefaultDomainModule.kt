package tuver.sudokusolver.di.impl

import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import tuver.sudokusolver.di.DomainModule
import tuver.sudokusolver.domain.ExtractBoardImageUseCase
import tuver.sudokusolver.domain.ExtractSudokuBoardUseCase
import tuver.sudokusolver.domain.GenerateSolutionImageUseCase
import tuver.sudokusolver.domain.SolveSudokuBoardUseCase
import tuver.sudokusolver.domain.impl.DefaultGenerateSolutionImageUseCase
import tuver.sudokusolver.domain.impl.DefaultSolveSudokuBoardUseCase
import tuver.sudokusolver.domain.impl.mlkit.MlKitExtractSudokuBoardUseCase
import tuver.sudokusolver.domain.impl.opencv.OpenCvExtractBoardImageUseCase
import tuver.sudokusolver.domain.impl.sudokusolver.BruteForceSudokuSolver

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