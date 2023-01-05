package tuver.sudokusolver.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tuver.sudokusolver.R
import tuver.sudokusolver.ui.sudokusolve.SudokuSolveFragment

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