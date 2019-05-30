package com.raqun.live.vibration

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.lifecycle.LiveData
import com.raqun.live.core.LiveResult

class VibrationLiveData(
        private val context: Context,
        private val vibrateTime: Long
) : LiveData<LiveResult<Unit>>() {

    init {
        vibratePhone()
    }

    private fun vibratePhone() {
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= 26) {
                postValue(LiveResult.LiveValue(vibrator.vibrate(VibrationEffect.createOneShot(vibrateTime, VibrationEffect.DEFAULT_AMPLITUDE))))
            } else {
                postValue(LiveResult.LiveValue(vibrator.vibrate(vibrateTime)))
            }
        }
    }
}