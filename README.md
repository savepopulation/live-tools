# Live-Tools
Live-Tools is a library project which aims to convert some Android device features to LiveData.

# List

## live-orientation ##
Observes orientation change even if device is forced to portrait mode. It works based on sensors.
```kotlin
OrientationLiveData(this)
      .observe(this, Observer {
           it?.let {
               Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show()
           }
      })
```

## live-location ##
Observes location changes. It observers coarse and fine location internally. If user's GPS setting is disabled, live-location notifies you about this. If location permission is needed, live-location checks this internally and notifies you about this.
```kotlin
locationLiveData = LocationLiveData(this)
locationLiveData.observe(this,
        Observer {
            when (it?.status) {
                LocationData.Status.PERMISSION_REQUIRED -> // request permission. (it.permissionList)
                LocationData.Status.ENABLE_SETTINGS -> //enable location settings (it.resolvableApiException)
                LocationData.Status.LOCATION_SUCCESS -> // location is ready to use (it.location)
            }
        })

//Start observing
locationLiveData.start()
```



### Contributors:<br>

<a href="https://github.com/iammert">Mert Şimşek</a><br>
<a href="https://github.com/EfeBudak">Efe Budak</a><br>
<a href="https://github.com/savepopulation">Taylan Sbrcn</a><br>
<a href="https://github.com/aykuttasil">Aykut Asil</a><br>

