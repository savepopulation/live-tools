package com.raqun.live_orientation

/**
 * Created by tyln on 19.02.2018.
 */
enum class DeviceOrientation(val value: Int) {
    LANDSCAPE_LEFT(1 shl 0),
    LANDSCAPE_RIGHT(1 shl 1),
    PORTRAIT(1 shl 3),
    UPSIDE_DOWN(1 shl 2),
    FACE_UP(1 shl 4),
    FACE_DOWN(1 shl 5);

    companion object {
        fun byValue(value: Int): DeviceOrientation? {
            return DeviceOrientation.values().firstOrNull { it.value == value }
        }
    }
}