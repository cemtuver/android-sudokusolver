package tuver.sudokusolver.util.extension

import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraCharacteristics.SENSOR_ORIENTATION

fun CameraCharacteristics.calculateRelativeOrientation(reference: Int): Int {
    val sensorOrientation = get(SENSOR_ORIENTATION) ?: 0
    val relativeOrientation = (reference + sensorOrientation + 360) % 360

    return relativeOrientation
}
