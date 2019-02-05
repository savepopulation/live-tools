package com.raqun.mediaselector

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.core.app.ActivityCompat

class PermissionUtils {

    companion object {

        fun getCameraPermissions(): List<String> = listOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        fun getGalleryPermissions(): List<String> = listOf(Manifest.permission.READ_EXTERNAL_STORAGE)

        fun needPermissionForGallery(context: Context): Boolean = !isPermissionsGranted(context, getGalleryPermissions())

        fun needPermissionForCamera(context: Context): Boolean = !isPermissionsGranted(context, getCameraPermissions())

        private fun isPermissionsGranted(context: Context, permissions: List<String>): Boolean {
            for (i in permissions.indices) {
                if (ActivityCompat.checkSelfPermission(context, permissions[i]) != PERMISSION_GRANTED) {
                    return false
                }
            }
            return true
        }
    }
}