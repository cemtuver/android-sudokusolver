package tuver.sudokusolver.domain

import android.graphics.Bitmap

interface ExtractBoardImageUseCase {

    suspend fun extractBoardImage(image: Bitmap): Bitmap

}