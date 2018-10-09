package com.raqun.live_media_selector

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.support.v4.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

val DATE_PATTERN_YMD_HMS = "yyyyMMdd_HHmmss"
val SUFFIX_JPEG = ".jpg"

inline fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat(DATE_PATTERN_YMD_HMS, Locale.getDefault()).format(Date())
    val imageFileName = "IMAGE_$timeStamp"
    val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(imageFileName, SUFFIX_JPEG, storageDir)
}

inline fun File.getUriFromFile(context: Context): Uri {
    return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
        FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", this)
    } else {
        Uri.fromFile(this)
    }
}