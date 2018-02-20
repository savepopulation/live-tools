package com.raqun.sensitiveactivity

/**
 * Created by tyln on 19.02.2018.
 */

object ScreenOrientations {

    val orientations = arrayOf(floatArrayOf(9.81f, 0f, 0f), // LandscapeLeft
            floatArrayOf(-9.81f, 0f, 0f), // LandscapeRight
            floatArrayOf(0f, -9.81f, 0f), // Portrait
            floatArrayOf(0f, 9.81f, 0f), // UpsideDown
            floatArrayOf(0f, 0f, -9.81f), // FaceUp
            floatArrayOf(0f, 0f, 9.81f)                                                              // FaceDown
    )
}
