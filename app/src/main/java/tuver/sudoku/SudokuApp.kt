package tuver.sudoku

import android.app.Application
import org.opencv.android.OpenCVLoader
import tuver.sudoku.di.AppModule

class SudokuApp : Application() {

    private fun initOpenCv() {
        OpenCVLoader.initDebug()
    }

    private fun initAppModule() {
        appModule = SudokuAppModule()
    }

    override fun onCreate() {
        super.onCreate()

        initOpenCv()
        initAppModule()
    }

    companion object {

        lateinit var appModule: AppModule
            private set

    }

}