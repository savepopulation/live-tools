package com.raqun.live.shake

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.LiveData
import com.raqun.live.core.LiveResult
import com.raqun.live.shake.ShakeConstants.SHAKE_COUNT_RESET_TIME_MS
import com.raqun.live.shake.ShakeConstants.SHAKE_SLOP_TIME_MS
import com.raqun.live.shake.ShakeConstants.SHAKE_THRESHOLD_GRAVITY

class ShakeLiveData : LiveData<LiveResult<Int>>(), SensorEventListener {


    private var shakeTimesStamp: Long = 0
    private var shakeCount: Int = 0

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        //no-op
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val x: Float = event!!.values[0]
        val y: Float = event.values[1]
        val z: Float = event.values[2]

        val gX = x / SensorManager.GRAVITY_EARTH
        val gY = y / SensorManager.GRAVITY_EARTH
        val gZ = z / SensorManager.GRAVITY_EARTH

        val gForce = (Math.sqrt((gX * gX + gY * gY + gZ * gZ).toDouble())).toFloat()

        if (gForce > SHAKE_THRESHOLD_GRAVITY) {
            val now = System.currentTimeMillis()
            if (shakeTimesStamp + SHAKE_SLOP_TIME_MS > now) {
                return
            }
            if (shakeTimesStamp + SHAKE_COUNT_RESET_TIME_MS < now) {
                shakeCount = 0
            }

            shakeTimesStamp = now
            shakeCount++

            value = LiveResult.LiveValue(shakeCount)

        }

    }
}