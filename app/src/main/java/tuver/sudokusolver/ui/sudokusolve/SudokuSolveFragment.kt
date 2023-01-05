package tuver.sudokusolver.ui.sudokusolve

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import tuver.sudokusolver.R
import tuver.sudokusolver.SudokuSolverApp.Companion.appModule
import tuver.sudokusolver.databinding.FragmentSudokuSolveBinding
import tuver.sudokusolver.util.extension.decodeBitmap
import tuver.sudokusolver.util.extension.makeGone
import tuver.sudokusolver.util.extension.makeInvisible
import tuver.sudokusolver.util.extension.makeVisible

class SudokuSolveFragment : Fragment(R.layout.fragment_sudoku_solve) {

    private val viewModel: SudokuSolveViewModel by viewModels { SudokuSolveViewModelFactory(appModule) }

    private var bindingField: FragmentSudokuSolveBinding? = null

    private val binding: FragmentSudokuSolveBinding
        get() = bindingField ?: throw IllegalAccessException("Binding is not initialized or valid")

    private val pickVisualMediaLauncher = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri == null) return@registerForActivityResult
        context?.decodeBitmap(uri)?.let { viewModel.onImagePick(it) }
    }

    private fun onViewStateChange(viewState: SudokuSolveViewState) {
        when (viewState) {
            is SudokuSolveViewState.Loading -> onLoadingViewState()
            is SudokuSolveViewState.Success -> onSuccessViewState(viewState)
        }
    }

    private fun onLoadingViewState() {
        binding.apply {
            progressBar.makeVisible()
            solutionImage.makeInvisible()
        }
    }

    private fun onSuccessViewState(successViewState: SudokuSolveViewState.Success) {
        binding.apply {
            progressBar.makeGone()
            solutionImage.makeVisible()
            solutionImage.setImageBitmap(successViewState.solutionImage)
        }
    }

    private fun onViewActionChange(viewAction: SudokuSolveViewAction) {
        when (viewAction) {
            is SudokuSolveViewAction.LaunchImagePick -> onLaunchImagePickAction()
        }
    }

    private fun onLaunchImagePickAction() {
        val request = PickVisualMediaRequest(
            ActivityResultContracts.PickVisualMedia.ImageOnly
        )

        pickVisualMediaLauncher.launch(request)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)?.also {
            bindingField = FragmentSudokuSolveBinding.bind(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.pickImageButton.setOnClickListener { viewModel.onPickImageButtonClick() }
        viewModel.viewState().observe(viewLifecycleOwner) { onViewStateChange(it) }
        viewModel.viewAction().observe(viewLifecycleOwner) { onViewActionChange(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingField = null
    }

    companion object {

        fun newInstance(): SudokuSolveFragment {
            return SudokuSolveFragment()
        }

    }

}
