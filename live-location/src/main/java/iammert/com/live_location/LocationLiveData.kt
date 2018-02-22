package iammert.com.live_location

import android.annotation.SuppressLint
import android.app.Activity
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.location.Location
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnSuccessListener
import java.util.jar.Manifest


/**
 * Created by mertsimsek on 21/02/2018.
 */
class LocationLiveData(private val activity: Activity) : LiveData<LocationData>() {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
    private val locationRequest = createLocationRequest()
    private val fineLocationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            super.onLocationResult(p0)
        }
    }

    override fun onActive() {
        super.onActive()
        startLocationUpdates()
    }

    override fun onInactive() {
        super.onInactive()
        stopLocationUpdates()
    }

    private fun startLocationUpdates() {
        if (PermissionUtils.checkLocationPermission(activity)) {
            fusedLocationClient.requestLocationUpdates(locationRequest, fineLocationCallback, null)
        } else {
            value = LocationData.permissionRequired(listOf(android.Manifest.permission.ACCESS_FINE_LOCATION))
        }
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(fineLocationCallback)
    }

    private fun createLocationRequest(): LocationRequest {
        val locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        return locationRequest
    }
}