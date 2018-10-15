package com.raqun.live_tools_core

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat

class PermissionUtil {
    companion object {
        /**
         * Permissions
         */
        val cameraPermissions = arrayOf(Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)

        val galleryPermissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)

        val locationPermissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)

        val connectionPermissions = arrayOf(Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE)

        /**
         * Permission check methods
         */
        fun isCameraPermissionsGranted(context: Context) = cameraPermissions.isPermissionsGranted(context)

        fun isGalleryPermissionsGranted(context: Context) = galleryPermissions.isPermissionsGranted(context)

        fun isLocationPermissionsGranted(context: Context) = locationPermissions.isPermissionsGranted(context)

        fun isConnectionPermissionsGranted(context: Context) = connectionPermissions.isPermissionsGranted(context)
    }
}

/**
 * Permission check extension
 */
fun Array<String>.isPermissionsGranted(context: Context): Boolean {
    for (i in this.indices) {
        if (ActivityCompat.checkSelfPermission(context, this[i]) != PackageManager.PERMISSION_GRANTED) {
            return false
        }
    }
    return true
}



