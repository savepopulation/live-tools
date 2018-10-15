package com.raqun.live_tools_core

import java.util.*

sealed class LiveResult {
    data class LiveValue<T>(val value: T?) : LiveResult()

    data class PermissionRequired(val requiredPermissions: Array<String>) : LiveResult() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is PermissionRequired) return false

            if (!Arrays.equals(requiredPermissions, other.requiredPermissions)) return false

            return true
        }

        override fun hashCode(): Int {
            return Arrays.hashCode(requiredPermissions)
        }
    }
}