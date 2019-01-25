package com.raqun.live_battery

/**
 * Created by tyln on 2.05.2018.
 */
data class BatteryInfo(val status: BatteryStatus,
                       val plug: BatteryPlug,
                       val level: Int,
                       val scale: Int,
                       val percentage: Float)