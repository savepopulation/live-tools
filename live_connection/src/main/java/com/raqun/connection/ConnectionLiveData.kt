package com.raqun.connection

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import androidx.lifecycle.LiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.raqun.live.core.LiveResult
import com.raqun.live.core.PermissionUtil

/**
 * Created by tyln on 22.02.2018.
 */
class ConnectionLiveData(private val context: Context) : LiveData<LiveResult<ConnectionType>>() {

    private val networkChangeReceiver: NetworkChangeReceiver

    init {
        networkChangeReceiver = NetworkChangeReceiver()
    }

    override fun onActive() {
        super.onActive()
        if (!PermissionUtil.isConnectionPermissionsGranted(context)) {
            postValue(LiveResult.PermissionRequired(PermissionUtil.connectionPermissions))
            return
        }
        LocalBroadcastManager.getInstance(context).registerReceiver(networkChangeReceiver,
                IntentFilter().apply {
                    addAction(ConnectivityManager.CONNECTIVITY_ACTION)
                    addAction(WifiManager.WIFI_STATE_CHANGED_ACTION)
                })
    }

    override fun onInactive() {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(networkChangeReceiver)
        super.onInactive()
    }

    private fun getConnectionStatus(): LiveResult<ConnectionType> {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


        val networkInfo: NetworkInfo? = cm.activeNetworkInfo as NetworkInfo
        return when (networkInfo?.type) {
            ConnectivityManager.TYPE_WIFI -> LiveResult.LiveValue(ConnectionType.WIFI)
            ConnectivityManager.TYPE_MOBILE -> LiveResult.LiveValue(ConnectionType.MOBILE)
            else -> LiveResult.LiveValue(ConnectionType.UNAVAILABLE)
        }
    }

    inner class NetworkChangeReceiver : BroadcastReceiver() {

        override fun onReceive(p0: Context?, p1: Intent?) {
            postValue(getConnectionStatus())
        }
    }
}