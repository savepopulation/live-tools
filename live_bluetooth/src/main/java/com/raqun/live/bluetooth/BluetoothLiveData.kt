package com.raqun.live.bluetooth

import androidx.lifecycle.LiveData
import com.raqun.live.core.LiveResult
import android.bluetooth.BluetoothDevice
import android.content.IntentFilter
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import com.raqun.live.core.PermissionUtil
import android.bluetooth.BluetoothAdapter


class BluetoothLiveData(
        private val context: Context
) : LiveData<LiveResult<BluetoothDeviceResult>>() {

    private var bluetoothConnectionReceiver: BroadcastReceiver? = null

    init {
        bluetoothConnectionReceiver = BluetoothBroadcastReceiver()
    }

    override fun onActive() {

        if (!PermissionUtil.isBluetoothConnectionPermissionsGranted(context)) {
            postValue(LiveResult.PermissionRequired(PermissionUtil.bluetoothConnectionPermissions))
            return
        }

        context.registerReceiver(bluetoothConnectionReceiver,
                IntentFilter().apply {
                    addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
                    addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED)
                    addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
                })

    }

    override fun onInactive() {
        context.unregisterReceiver(bluetoothConnectionReceiver)
        super.onInactive()
    }

    /*private fun getConnectionStatus(): LiveResult<BluetoothDeviceResult> {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


        val networkInfo: NetworkInfo? = cm.activeNetworkInfo as NetworkInfo
        return when (networkInfo?.type) {
            ConnectivityManager.TYPE_WIFI -> LiveResult.LiveValue(ConnectionType.WIFI)
            ConnectivityManager.TYPE_MOBILE -> LiveResult.LiveValue(ConnectionType.MOBILE)
            else -> LiveResult.LiveValue(ConnectionType.UNAVAILABLE)
        }
    }*/

    inner class BluetoothBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action

            when (action) {
                BluetoothDevice.ACTION_FOUND -> {

                }
                BluetoothDevice.ACTION_ACL_CONNECTED -> {

                }
                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {

                }
                BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED -> {

                }
                BluetoothDevice.ACTION_ACL_DISCONNECTED -> {

                }
            }
        }
    }
}