package iammert.com.app

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.iammert.live_tools_common.LiveResult
import com.raqun.live_battery.BatteryInfo
import com.raqun.live_battery.BatteryLiveData

class LiveBatteryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BatteryLiveData(this)
                .observe(this, Observer {
                    when (it) {
                        is LiveResult.LiveValue<*> -> handleBatteryInfo(it as BatteryInfo?)
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