package com.iammert.live_video_frames

import android.arch.lifecycle.LiveData
import android.content.Context
import com.iammert.live_tools_common.PermissionUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VideoFramesLiveData(private val context: Context,
                          private val videoFramesConfig: FrameConfig = FrameConfig.default()) : LiveData<VideoData>() {

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