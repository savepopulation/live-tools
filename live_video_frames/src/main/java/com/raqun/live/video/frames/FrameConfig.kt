package com.raqun.live.video.frames

data class FrameConfig(val frameDurationInMillis: Long, val frameWidth: Float, val frameHeight: Float){

    companion object {
        fun default(): FrameConfig = FrameConfig(4000L, 400f, 300f)
    }
}