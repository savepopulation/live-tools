package com.raqun.sensitiveactivity

/**
 * Created by tyln on 19.02.2018.
 */
enum class DeviceOrientation(val value: Int) {
    LANDSCAPELEFT(1 shl 0),
    LANDSCAPERIGHT(1 shl 1),
    PORTRAIT(1 shl 2),
    UPSIDEDOWN(1 shl 3),
    FACEUP(1 shl 4),
    FACEDOWN(1 shl 5)
}