package com.raqun.sensitiveactivity

import android.arch.lifecycle.LiveData
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

/**
 * Created by tyln on 20.02.2018.
 */
class OrientationLiveData(context: Context) : LiveData<DeviceOrientation>(), SensorEventListener {

    private val sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer: Sensor

    private var currentOrientation: DeviceOrientation? = null
    private val defaultOrientationsSet = Constants.deviceOrientationAllLandscape or Constants.deviceOrientationAllPortrait

    init {
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun onActive() {
        super.onActive()
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onInactive() {
        sensorManager.unregisterListener(this)
        super.onInactive()
    }

    override fun getValue(): DeviceOrientation? {
        return currentOrientation
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val x1 = event!!.values[0]
        val y1 = event.values[1]
        val z1 = event.values[2]
        val threshold = 3.5f
        var currentOrientationValue = -1

        for (i in 0..5) {
            val x = Constants.orientations[i][0]
            val y = Constants.orientations[i][1]
            val z = Constants.orientations[i][2]
            if (x1 > x - threshold
                    && x1 < x + threshold
                    && y1 > y - threshold
                    && y1 < y + threshold
                    && z1 > z - threshold * 2.0
                    && z1 < z + threshold * 2.0) {
                currentOrientationValue = 1 shl i
                break
            }
        }

        if (currentOrientationValue != this.currentOrientation?.value) {
            val orientationChanged = when (currentOrientationValue) {
                DeviceOrientation.LANDSCAPE_LEFT.value ->
                    currentOrientationValue and this.defaultOrientationsSet == DeviceOrientation.LANDSCAPE_LEFT.value
                DeviceOrientation.LANDSCAPE_RIGHT.value ->
                    currentOrientationValue and this.defaultOrientationsSet == DeviceOrientation.LANDSCAPE_RIGHT.value
                DeviceOrientation.PORTRAIT.value ->
                    currentOrientationValue and this.defaultOrientationsSet == DeviceOrientation.PORTRAIT.value
                DeviceOrientation.UPSIDE_DOWN.value ->
                    currentOrientationValue and this.defaultOrientationsSet == DeviceOrientation.UPSIDE_DOWN.value
                DeviceOrientation.FACE_UP.value ->
                    currentOrientationValue and this.defaultOrientationsSet == DeviceOrientation.FACE_UP.value
                DeviceOrientation.FACE_DOWN.value ->
                    currentOrientationValue and this.defaultOrientationsSet == DeviceOrientation.FACE_DOWN.value
                else -> false
            }

            if (orientationChanged) {
                this.currentOrientation = DeviceOrientation.values()[currentOrientationValue]
                value = currentOrientation
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        // no-op
    }

}
