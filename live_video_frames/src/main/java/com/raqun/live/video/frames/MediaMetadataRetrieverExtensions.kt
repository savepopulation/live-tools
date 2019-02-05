package com.raqun.live.video.frames

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever

fun MediaMetadataRetriever.getDuration() = extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION).toLong()

fun MediaMetadataRetriever.getScaledFrame(timeInMillis: Long, frameWidth: Float, frameHeight: Float): Bitmap {
    val bitmap = getFrameAtTime(timeInMillis * 1000, MediaMetadataRetriever.OPTION_CLOSEST)
    val bitmapWidth = bitmap.width
    val bitmapHeight = bitmap.height
    val min = Math.min(frameWidth, frameHeight)
    val bitmapMin = Math.min(bitmapWidth.toFloat(), bitmapHeight.toFloat())
    val scale = min / bitmapMin
    val scaledBitmap = Bitmap.createScaledBitmap(bitmap, (bitmapWidth * scale).toInt(), (bitmapHeight * scale).toInt(), true)
    bitmap.recycle()
    return scaledBitmap
}