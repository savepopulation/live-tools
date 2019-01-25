package com.raqun.live_media_selector

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.LiveData
import java.io.File
import java.io.IOException

class MediaSelectorLiveData(private val activity: Activity) : LiveData<MediaData>() {

    private var photoURI: Uri? = null

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (Activity.RESULT_OK != resultCode) {
            return
        }

        if (requestCode != RC_INTENT_GALLERY && requestCode != RC_INTENT_CAMERA) {
            return
        }

        when (requestCode) {
            RC_INTENT_GALLERY -> if (data?.data != null) photoURI = data.data
        }

        value = if (photoURI != null) MediaData.success(photoURI) else MediaData.error(IllegalArgumentException("Photo URI is empty"))
    }

    fun openCamera() {
        if (PermissionUtils.needPermissionForCamera(activity)) {
            value = MediaData.cameraPermissionRequired()
            return
        }

        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePictureIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION

        if (takePictureIntent.resolveActivity(activity.packageManager) != null) {
            var photoFile: File? = null
            try {
                photoFile = activity.createImageFile()
            } catch (ex: IOException) {
                value = MediaData.error(ex)
            }

            if (photoFile != null) {
                photoURI = photoFile.getUriFromFile(activity)
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(activity, takePictureIntent, RC_INTENT_CAMERA, null)
            }
        }
    }

    fun openGallery() {
        if (PermissionUtils.needPermissionForGallery(activity)) {
            value = MediaData.galleryPermissionRequired()
            return
        }

        val pickPhoto = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(activity, pickPhoto, RC_INTENT_GALLERY, null)
    }

    companion object {
        private const val RC_INTENT_CAMERA = 241
        private const val RC_INTENT_GALLERY = 242
    }
}