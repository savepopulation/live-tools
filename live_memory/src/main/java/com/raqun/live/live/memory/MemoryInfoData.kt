package com.raqun.live.live.memory

data class MemoryInfoData(
    var availableMemory: Long? = null,
    var totalMemory: Long? = null,
    var percentAvailable: String? = null,
    var percentAvailableRounded: Int? = null,
    var runtimeMaxMemory: Long? = null,
    var runtimeTotalMemory: Long? = null,
    var runtimeFreeMemory: Long? = null
)