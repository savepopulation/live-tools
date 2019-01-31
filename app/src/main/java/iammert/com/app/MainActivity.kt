package iammert.com.app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.iammert.live_tools_common.LiveResult
import com.iammert.live_tools_common.PermissionUtil
import com.raqun.live_orientation.DeviceOrientation
import com.raqun.live_orientation.OrientationLiveData
import iammert.com.live_location.LocationData
import iammert.com.live_location.LocationLiveData

class MainActivity : AppCompatActivity() {

    private lateinit var locationLiveData: LocationLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        OrientationLiveData(this)
                .observe(this, Observer {
                    when (it) {
                        is LiveResult.LiveValue<DeviceOrientation> -> Toast.makeText(this,
                                it.value.toString(), Toast.LENGTH_SHORT).show()
                    }
                })

        locationLiveData = LocationLiveData(this)
        locationLiveData.observe(this,
                Observer {
                    when (it?.status) {
                        LocationData.Status.PERMISSION_REQUIRED -> requestPermissions(it.permissionList)
                        LocationData.Status.ENABLE_SETTINGS -> Log.e("error", "resolvable exception")//enableLocationSettings(it.resolvableApiException)
                    }
                })

        locationLiveData.start()
    }

    private fun requestPermissions(permissionList: Array<String?>) {
        ActivityCompat.requestPermissions(this, permissionList, REQUEST_CODE_LOCATION_PERMISSION)
    }

    /*
    private fun enableLocationSettings(exception: ResolvableApiException?) {
        exception?.startResolutionForResult(this, REQUEST_CODE_LOCATION_SETTINGS)
    }*/

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (PermissionUtil.isPermissionResultsGranted(grantResults)) {
            locationLiveData.start()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_LOCATION_SETTINGS && resultCode == Activity.RESULT_OK) {
            locationLiveData.start()
        }
    }

    companion object {
        const val REQUEST_CODE_LOCATION_PERMISSION = 12
        const val REQUEST_CODE_LOCATION_SETTINGS = 13
    }
}
