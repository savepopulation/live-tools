package iammert.com.live_location

import android.app.Activity
import android.arch.lifecycle.LiveData
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener


/**
 * Created by mertsimsek on 21/02/2018.
 */
class LocationLiveData(private val activity: Activity) : LiveData<LocationData>() {

    private val mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)

    private val lastLocationListener = object : OnSuccessListener<Location> {
        override fun onSuccess(p0: Location?) {

        }
    }

    override fun onActive() {
        super.onActive()
    }

    override fun onInactive() {
        super.onInactive()
    }

}