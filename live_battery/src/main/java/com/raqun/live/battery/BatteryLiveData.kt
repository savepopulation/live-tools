package com.raqun.live.battery

import android.content.Context
import android.os.BatteryManager
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.IntentFilter
import androidx.lifecycle.LiveData
import com.raqun.live.core.LiveResult

/**
 * Created by tyln on 2.05.2018.
 */
class BatteryLiveData(private val context: Context) : LiveData<LiveResult<BatteryInfo>>() {

    private val powerConnectionReceiver: PowerConnectionReceiver

    init {
        powerConnectionReceiver = PowerConnectionReceiver()
    }

    override fun onActive() {
        super.onActive()
        context.registerReceiver(powerConnectionReceiver,
                IntentFilter().apply {
                    addAction(Intent.ACTION_POWER_CONNECTED)
                    addAction(Intent.ACTION_POWER_DISCONNECTED)
                    addAction(Intent.ACTION_BATTERY_CHANGED)
                })
    }

    override fun onInactive() {
        context.unregisterReceiver(powerConnectionReceiver)
        super.onInactive()
    }

    private fun publishBatteryInfo(intent: Intent) {
        val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, NO_VAL)
        val batteryStatus = when (status) {
            BatteryManager.BATTERY_STATUS_CHARGING -> BatteryStatus.CHARGING
            BatteryManager.BATTERY_STATUS_FULL -> BatteryStatus.FULL
            else -> BatteryStatus.NOT_CHARGING
        }

        val chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, NO_VAL)
        val batteryPlug = when (chargePlug) {
            BatteryManager.BATTERY_PLUGGED_AC -> BatteryPlug.AC
            BatteryManager.BATTERY_PLUGGED_USB -> BatteryPlug.USB
            BatteryManager.BATTERY_PLUGGED_WIRELESS -> BatteryPlug.WIRELESS
            else -> BatteryPlug.NOT_RECOGNISED
        }

        val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, NO_VAL)
        val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, NO_VAL)
        val prc = 100 * (level / scale.toFloat())

        postValue(LiveResult.LiveValue(BatteryInfo(batteryStatus, batteryPlug, level, scale, prc)))
    }

    inner class PowerConnectionReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            publishBatteryInfo(intent)
        }
    }

    companion object {
        private const val NO_VAL = -1
    }
}