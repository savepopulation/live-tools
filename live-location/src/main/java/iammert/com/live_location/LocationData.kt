package iammert.com.live_location

import android.location.Location
import com.google.android.gms.common.ConnectionResult

/**
 * Created by mertsimsek on 21/02/2018.
 */

class LocationData private constructor(val status: Status, val location: Location?, val connectionResult: ConnectionResult?) {

    enum class Status {
        SUCCESS, ERROR
    }

    companion object {

        fun success(location: Location): LocationData {
            return LocationData(Status.SUCCESS, location, null)
        }

        fun error(connectionResult: ConnectionResult): LocationData {
            return LocationData(Status.ERROR, null, connectionResult)
        }
    }
}

