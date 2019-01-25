package com.raqun.live_media_selector

import android.net.Uri
import java.util.*

data class MediaData(val status: Status,
                     val uri: Uri? = null,
                     val exception: Exception? = null,
                     val permissionList: List<String> = Collections.emptyList()) {

    enum class Status {
        MEDIA_SUCCESS, PERMISSION_REQUIRED, ERROR
    }

    companion object {

        fun success(uri: Uri?): MediaData = MediaData(
                status = Status.MEDIA_SUCCESS,
                uri = uri)

        fun error(exception: Exception): MediaData = MediaData(
                status = Status.ERROR,
                exception = exception)

        fun cameraPermissionRequired(): MediaData = MediaData(
                status = Status.PERMISSION_REQUIRED,
                permissionList = PermissionUtils.getCameraPermissions())

        fun galleryPermissionRequired(): MediaData = MediaData(
                status = Status.PERMISSION_REQUIRED,
                permissionList = PermissionUtils.getGalleryPermissions())
    }
}