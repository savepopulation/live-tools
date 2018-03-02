package com.aykuttasil.livelocation

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult


/**
 * Created by aykutasil on 27.12.2017.
 */
class LocationLiveData constructor(val context: Context) : LiveData<Location>() {

    private var fusedLocationProvider = FusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    override fun onActive() {
        super.onActive()
        val isGranted = context.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
        if (isGranted == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProvider.locationAvailability.addOnSuccessListener {
                if (it.isLocationAvailable) {
                    fusedLocationProvider.requestLocationUpdates(createLocationRequest(), locationCallback, null)

                    fusedLocationProvider.lastLocation.addOnSuccessListener {
                        value = it
                    }
                }
            }
        } else {
            Toast.makeText(context, "Require Location Permission", Toast.LENGTH_SHORT).show()
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult?.locations?.forEach {
                Log.i("loc", it.toString())
                value = it
            }
        }
    }

    private fun createLocationRequest(): LocationRequest {
        return LocationRequest().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    override fun onInactive() {
        super.onInactive()
        fusedLocationProvider.removeLocationUpdates(locationCallback)
    }
}