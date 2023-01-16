package tuver.sudokusolver

import android.app.Application
import org.opencv.android.OpenCVLoader
import tuver.sudokusolver.di.AppModule
import tuver.sudokusolver.di.impl.AppModuleImpl

class SudokuSolverApp : Application() {

    private fun initOpenCv() {
        OpenCVLoader.initDebug()
    }

    private fun initAppModule() {
        appModule = AppModuleImpl()
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