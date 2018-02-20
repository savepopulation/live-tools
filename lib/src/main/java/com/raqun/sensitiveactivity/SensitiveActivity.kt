package com.raqun.sensitiveactivity

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity


/**
 * Created by tyln on 19.02.2018.
 */
abstract class SensitiveActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer: Sensor

    private var currentOrientation: DeviceOrientation? = null
    private val defaultOrientationsSet = Constants.deviceOrientationAllLandscape or Constants.deviceOrientationAllPortrait

    abstract fun onOrientationChanged(deviceOrientation: DeviceOrientation?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    public override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    public override fun onPause() {
        sensorManager.unregisterListener(this)
        super.onPause()
    }

    override fun onSensorChanged(event: SensorEvent) {
        val x1 = event.values[0]
        val y1 = event.values[1]
        val z1 = event.values[2]
        val threshold = 3.5f
        var currentOrientationValue = -1

        for (i in 0..5) {
            val x = ScreenOrientations.orientations[i][0]
            val y = ScreenOrientations.orientations[i][1]
            val z = ScreenOrientations.orientations[i][2]
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
                    currentOrientationValue and this.defaultOrientationsSet == DeviceOrientation.LANDSCAPELEFT.value
                DeviceOrientation.LANDSCAPE_RIGHT.value ->
                    currentOrientationValue and this.defaultOrientationsSet == DeviceOrientation.LANDSCAPERIGHT.value
                DeviceOrientation.PORTRAIT.value ->
                    currentOrientationValue and this.defaultOrientationsSet == DeviceOrientation.PORTRAIT.value
                DeviceOrientation.UPSIDE_DOWN.value ->
                    currentOrientationValue and this.defaultOrientationsSet == DeviceOrientation.UPSIDEDOWN.value
                DeviceOrientation.FACE_UP.value ->
                    currentOrientationValue and this.defaultOrientationsSet == DeviceOrientation.FACEUP.value
                DeviceOrientation.FACE_DOWN.value ->
                    currentOrientationValue and this.defaultOrientationsSet == DeviceOrientation.FACEDOWN.value
                else -> false

            }

            if (orientationChanged) {
                this.currentOrientation = DeviceOrientation.values()[currentOrientationValue]
                onOrientationChanged(currentOrientation)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // no-op
    }

    fun getCurrentOrientation() = this.currentOrientation
}