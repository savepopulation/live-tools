package iammert.com.live_location

import android.app.Activity
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.location.Location
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*


/**
 * Created by mertsimsek on 21/02/2018.
 */
class LocationLiveData(private val activity: Activity) : MediatorLiveData<LocationData>() {

    /**
     * LiveData
     */
    private val coarseLocationLiveData = MutableLiveData<Location>()
    private val fineLocationLiveData = MutableLiveData<Location>()

    /**
     * Location Request and Client
     */
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
    private val locationRequest = createLocationRequest()

    /**
     * Location Settings
     */
    private val locationSettingsBuilder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
    private val settingsClient = LocationServices.getSettingsClient(activity)
    private var isStarted: Boolean = false

    /**
     * Callback
     */
    private val fineLocationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult?) {
            super.onLocationResult(result)
            removeSource(coarseLocationLiveData)
            fineLocationLiveData.value = result?.lastLocation
        }
    }

    /**
     * Initialization
     */
    init {
        addSource(coarseLocationLiveData, { value = LocationData.success(it) })
        addSource(fineLocationLiveData, { value = LocationData.success(it) })
    }

    override fun onActive() {
        super.onActive()
        if (isStarted) {
            startLocationUpdates()
        }
    }

    override fun onInactive() {
        super.onInactive()
        stopLocationUpdates()
    }

    fun start() {
        isStarted = true
        startLocationUpdates()
    }

    /**
     * Checks runtime permission first.
     * Then check if user is enable gps settings.
     * If all good, then start listening user location
     * and update livedata
     */
    private fun startLocationUpdates() {
        if (!PermissionUtils.checkLocationPermission(activity)) {
            value = LocationData.permissionRequired(listOf(android.Manifest.permission.ACCESS_FINE_LOCATION))
            return
        }

        val settingsTask = settingsClient.checkLocationSettings(locationSettingsBuilder.build())

        settingsTask.addOnSuccessListener {
            fusedLocationClient.lastLocation.addOnSuccessListener { it?.let { coarseLocationLiveData.value = it } }
            fusedLocationClient.requestLocationUpdates(locationRequest, fineLocationCallback, null)
        }

        settingsTask.addOnFailureListener {
            value = if (it is ResolvableApiException) LocationData.settingsRequired(it) else LocationData.error(it)
        }
    }

    /**
     * Removes listener onInactive
     */
    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(fineLocationCallback)
    }

    /**
     * Creates a LocationRequest model
     */
    private fun createLocationRequest(): LocationRequest {
        val locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        return locationRequest
    }
}