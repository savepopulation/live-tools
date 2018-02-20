package iammert.com.app

import android.os.Bundle
import com.raqun.sensitiveactivity.DeviceOrientation
import com.raqun.sensitiveactivity.SensitiveActivity

class MainActivity : SensitiveActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onOrientationChanged(deviceOrientation: DeviceOrientation?) {
        
    }
}
