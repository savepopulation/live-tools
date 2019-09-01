package iammert.com.app

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.raqun.live.core.LiveResult
import com.raqun.live.orientation.DeviceOrientation
import com.raqun.live.orientation.OrientationLiveData

class LiveOrientationActivitty : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orientation)

        OrientationLiveData(this)
                .observe(this, Observer {
                    when (it) {
                        is LiveResult.LiveValue<DeviceOrientation> -> Toast.makeText(this,
                                it.value.toString(), Toast.LENGTH_SHORT).show()
                    }
                })
    }
}