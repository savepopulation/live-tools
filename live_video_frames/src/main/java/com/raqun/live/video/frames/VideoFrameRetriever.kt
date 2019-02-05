package com.raqun.live.video.frames

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever

class VideoFrameRetriever(private val videoFrameConfig: FrameConfig) {

    fun retrieve(videoPath: String): VideoData {
        val frameList = ArrayList<FrameData>()

        val mediaMetadataRetriever = MediaMetadataRetriever()
        mediaMetadataRetriever.setDataSource(videoPath)

        val totalDuration = mediaMetadataRetriever.getDuration()

        var currentTime: Long = 0
        val totalFrameCount: Int = Math.ceil((totalDuration.toDouble() / videoFrameConfig.frameDurationInMillis)).toInt()

        for (i in 1..totalFrameCount) {
            val timeLeft = totalDuration - currentTime
            if (timeLeft < videoFrameConfig.frameDurationInMillis) {
                val ratio = timeLeft.toFloat() / videoFrameConfig.frameDurationInMillis.toFloat()
                mediaMetadataRetriever.getScaledFrame(currentTime, videoFrameConfig.frameWidth, videoFrameConfig.frameHeight)
                        .run { Bitmap.createBitmap(this, 0, 0, (width * ratio).toInt(), height) }
                        .also { frameList.add(FrameData(it, ratio)) }
            } else {
                mediaMetadataRetriever.getScaledFrame(currentTime, videoFrameConfig.frameWidth, videoFrameConfig.frameHeight)
                        .also { frameList.add(FrameData(it, 1.0f)) }
            }

            currentTime += videoFrameConfig.frameDurationInMillis
        }
        mediaMetadataRetriever.release()
        return VideoData(
                status = VideoData.Status.SUCCESS,
                videoPath = videoPath,
                videoDuration = totalDuration,
                videoFrames = frameList,
                videoFrameConfig = videoFrameConfig)
    }
}