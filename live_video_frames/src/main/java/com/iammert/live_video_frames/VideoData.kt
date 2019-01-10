package com.iammert.live_video_frames

class VideoData(
        val status: Status,
        val videoPath: String = "",
        val videoDuration: Long = 0L,
        val videoFrames: List<FrameData> = arrayListOf(),
        val videoFrameConfig: FrameConfig = FrameConfig.default(),
        val permissionList: Array<String> = emptyArray()) {

    enum class Status {
        SUCCESS, PERMISSION_REQUIRED
    }
}