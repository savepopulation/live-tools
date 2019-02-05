package com.iammert.live_video_frames

import android.content.Context
import androidx.lifecycle.LiveData
import com.raqun.live.core.PermissionUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VideoFramesLiveData(private val context: Context,
                          videoFramesConfig: FrameConfig = FrameConfig.default()) : LiveData<VideoData>() {

    private val uiScope = CoroutineScope(Dispatchers.Main)

    private val videoFrameRetriever = VideoFrameRetriever(videoFramesConfig)

    fun retrieveFrames(videoPath: String) {
        if (isStoragePermissionsGranted().not()) {
            value = VideoData(
                    status = VideoData.Status.PERMISSION_REQUIRED,
                    permissionList = PermissionUtil.storagePermissions)
            return
        }

        uiScope.launch {
            val videoData = withContext(Dispatchers.IO) { videoFrameRetriever.retrieve(videoPath) }
            value = videoData
        }
    }

    private fun isStoragePermissionsGranted(): Boolean = PermissionUtil.isStoragePermissionGranted(context)
}