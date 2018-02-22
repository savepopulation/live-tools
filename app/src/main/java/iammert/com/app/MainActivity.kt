package iammert.com.app

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import iammert.com.live_location.LocationData
import iammert.com.live_location.LocationLiveData

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LocationLiveData(this).observe(this,
                Observer {
                    when (it?.status) {
                        LocationData.Status.PERMISSION_REQUIRED -> requestPermissions(it.permissionList)
                    }
                })
    }

    private fun requestPermissions(permissionList: Array<String?>) {
        ActivityCompat.requestPermissions(this, permissionList, REQUEST_CODE_LOCATION_PERMISSION)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

    companion object {
        const val REQUEST_CODE_LOCATION_PERMISSION = 12
    }
}
