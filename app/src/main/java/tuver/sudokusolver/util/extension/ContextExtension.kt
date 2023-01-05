package tuver.sudokusolver.util.extension

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore

fun Context.decodeBitmap(uri: Uri): Bitmap {
    return when {
        Build.VERSION.SDK_INT < Build.VERSION_CODES.Q -> MediaStore.Images.Media.getBitmap(contentResolver, uri)
        else -> ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver, uri))
    }
}