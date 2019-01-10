package iammert.com.app

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.iammert.live_tools_common.PermissionUtil
import com.iammert.live_video_frames.VideoData
import com.iammert.live_video_frames.VideoFramesLiveData

class LiveVideoFramesActivity : AppCompatActivity() {

    private lateinit var videoFramesLiveData: VideoFramesLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        videoFramesLiveData = VideoFramesLiveData(this)
        videoFramesLiveData.retrieveFrames(SAMPLE_VIDEO_PATH)
        videoFramesLiveData.observe(this, Observer {
            when (it?.status) {
                VideoData.Status.PERMISSION_REQUIRED -> requestStorage(it.permissionList)
                VideoData.Status.SUCCESS -> Log.v("TEST", "${it.videoFrames.size}")
            }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (PermissionUtil.isPermissionResultsGranted(grantResults)) {
            videoFramesLiveData.retrieveFrames(SAMPLE_VIDEO_PATH)
        }
    }

    private fun requestStorage(permissions: Array<String>) =
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_STORAGE_PERMISSION)

    companion object {
        const val REQUEST_CODE_STORAGE_PERMISSION = 14
        const val SAMPLE_VIDEO_PATH = "YOUR_LOCAL_VIDEO_PATH"
    }
}