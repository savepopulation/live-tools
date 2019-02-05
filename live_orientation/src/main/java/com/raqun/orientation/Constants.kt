package com.raqun.orientation

/**
 * Created by tyln on 19.02.2018.
 */
object Constants {
    val deviceOrientationAllLandscape = DeviceOrientation.LANDSCAPE_LEFT.value or DeviceOrientation.LANDSCAPE_RIGHT.value
    val deviceOrientationAllPortrait = DeviceOrientation.PORTRAIT.value or DeviceOrientation.UPSIDE_DOWN.value
    val orientations = arrayOf(floatArrayOf(9.81f, 0f, 0f), // LandscapeLeft
            floatArrayOf(-9.81f, 0f, 0f), // LandscapeRight
            floatArrayOf(0f, -9.81f, 0f), // Portrait
            floatArrayOf(0f, 9.81f, 0f), // UpsideDown
            floatArrayOf(0f, 0f, -9.81f), // FaceUp
            floatArrayOf(0f, 0f, 9.81f)   // FaceDown
    )
}
