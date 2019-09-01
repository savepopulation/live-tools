package com.raqun.live.live.memory

import android.app.ActivityManager
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import androidx.lifecycle.LiveData
import com.raqun.live.core.LiveResult
import java.util.*

class MemoryInfoLiveData(private val context: Context, private val updatePeriod: Long = PERIOD) :
        LiveData<LiveResult<MemoryInfoData>>() {

    private val memoryInfo by lazy {
        ActivityManager.MemoryInfo()
    }

    private val activityManager by lazy {
        context.getSystemService(ACTIVITY_SERVICE) as ActivityManager?
    }

    private val runtime by lazy {
        Runtime.getRuntime()
    }

    private lateinit var timer: Timer

    override fun onActive() {
        super.onActive()
        timer = Timer()
        timer.scheduleAtFixedRate(
                object : TimerTask() {
                    override fun run() {
                        postMemoryInfoLiveData()
                    }
                },
                0,
                updatePeriod
        )
    }

    override fun onInactive() {
        timer.cancel()
        super.onInactive()
    }


    private fun postMemoryInfoLiveData() {
        activityManager?.getMemoryInfo(memoryInfo)
        postValue(
                memoryInfoLiveData {
                    totalMemory = memoryInfo.totalMem
                    availableMemory = memoryInfo.availMem
                    percentAvailableRounded =
                            (100 * (totalMemory!! - availableMemory!!) / totalMemory!!).toInt()
                    runtimeMaxMemory = runtime.maxMemory() / 1048576L
                    runtimeTotalMemory = runtime.totalMemory()
                    runtimeFreeMemory = runtime.freeMemory()
                }
        )
    }

    private fun memoryInfoLiveData(block: MemoryInfoData.() -> Unit): LiveResult<MemoryInfoData> {
        val memoryInfo = MemoryInfoData()
        memoryInfo.block()
        return LiveResult.LiveValue(memoryInfo)
    }

    companion object {

        const val PERIOD = 1000L
    }
}