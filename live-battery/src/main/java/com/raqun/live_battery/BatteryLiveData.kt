package com.raqun.live_battery

import android.arch.lifecycle.LiveData
import android.content.Context
import android.os.BatteryManager
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.support.v4.content.LocalBroadcastManager


/**
 * Created by tyln on 2.05.2018.
 */
class BatteryLiveData(private val context: Context) : LiveData<BatteryInfo>() {

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
        val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
        val batteryStatus = when (status) {
            BatteryManager.BATTERY_STATUS_CHARGING -> BatteryStatus.CHARGING
            BatteryManager.BATTERY_STATUS_FULL -> BatteryStatus.FULL
            else -> BatteryStatus.NOT_CHARGING
        }

        val chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)
        val batteryPlug = when (chargePlug) {
            BatteryManager.BATTERY_PLUGGED_AC -> BatteryPlug.AC
            BatteryManager.BATTERY_PLUGGED_USB -> BatteryPlug.USB
            BatteryManager.BATTERY_PLUGGED_WIRELESS -> BatteryPlug.WIRELESS
            else -> BatteryPlug.NOT_RECOGNISED
        }

        val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        val prc = 100 * (level / scale.toFloat())

        postValue(BatteryInfo(batteryStatus, batteryPlug, level, scale, prc))
    }

    inner class PowerConnectionReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            publishBatteryInfo(intent)
        }
    }
}