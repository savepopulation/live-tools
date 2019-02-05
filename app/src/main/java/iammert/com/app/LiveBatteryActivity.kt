package iammert.com.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.raqun.live.core.LiveResult
import com.raqun.live.battery.BatteryInfo
import com.raqun.live.battery.BatteryLiveData

class LiveBatteryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BatteryLiveData(this)
                .observe(this, Observer {
                    when (it) {
                        is LiveResult.LiveValue<BatteryInfo> -> handleBatteryInfo(it.value)
                    }
                })
    }

    private fun handleBatteryInfo(batteryInfo: BatteryInfo?) {
        batteryInfo?.let {
            alert("Status: " + it.status +
                    " Plug: " + it.plug +
                    " Level: " + it.level +
                    " Scale: " + it.scale +
                    " Percentage " + it.percentage)
        }
    }
}