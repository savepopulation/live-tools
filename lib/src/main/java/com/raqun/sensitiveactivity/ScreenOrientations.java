package com.raqun.sensitiveactivity;

/**
 * Created by tyln on 19.02.2018.
 */

public final class ScreenOrientations {

    private ScreenOrientations() {
        // No instance available
    }

    public static final float[][] orientations = {
            {9.81f, 0, 0},                                                             // LandscapeLeft
            {-9.81f, 0, 0},                                                            // LandscapeRight
            {0, -9.81f, 0},                                                            // Portrait
            {0, 9.81f, 0},                                                             // UpsideDown
            {0, 0, -9.81f},                                                            // FaceUp
            {0, 0, 9.81f}                                                              // FaceDown
    };
}
