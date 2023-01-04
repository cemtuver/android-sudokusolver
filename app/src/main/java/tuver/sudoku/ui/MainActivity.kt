package tuver.sudoku.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tuver.sudoku.R
import tuver.sudoku.ui.sudokusolve.SudokuSolveFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SudokuSolveFragment.newInstance())
                .commitNow()
        }
    }

}