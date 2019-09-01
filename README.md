# Live-Tools
Live-Tools is a library project which aims to convert some Android device features to LiveData.

# Live Tool List
- **Live Orientation:** Observe orientation changes
- **Live Location:** Observe user location (fine and coarse location together)
- **Live Battery:** Observe battery level, plug state, scale and percentage 
- **Live Media Selector:** Observe taken photo or selected media URI.
- **Live Video Frames:** Fetch Frame List (bitmaps) of given video path.

# Usage

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

## live-battery
Observes battery changes, state, scale, level and plug info.
```kotlin
BatteryLiveData(this)
                .observe(this, Observer {
                    it?.let {
                        Toast.makeText(this, "Status: " + it.status +
                                " Plug: " + it.plug +
                                " Level: " + it.level +
                                " Scale: " + it.scale +
                                " Percentage " + it.percentage, Toast.LENGTH_LONG).show()
                    }
})
```

## live-media-selector

Observes picked camera or gallery image uri.
```kotlin
MediaSelectorLiveData(this)
      .observe(this, Observer {
                when (it?.status) {
                    MediaData.Status.MEDIA_SUCCESS -> { it.uri }
                    MediaData.Status.PERMISSION_REQUIRED -> { it.permissionList }
                    MediaData.Status.ERROR -> { it.exception }
                }
            })
```

```kotlin
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
      super.onActivityResult(requestCode, resultCode, data)
      mediaSelectorLiveData.onActivityResult(requestCode, resultCode, data)
}
```

```kotlin
mediaSelectorLiveData.openCamera()
mediaSelectorLiveData.openGallery()
```

## Live Video Frames

Observes video frames as bitmap list.
```kotlin
val videoFramesLiveData = VideoFramesLiveData(this)
videoFramesLiveData.retrieveFrames(SAMPLE_VIDEO_PATH)
videoFramesLiveData.observe(this, Observer {
      when (it?.status) {
          VideoData.Status.PERMISSION_REQUIRED -> requestStorage(it.permissionList)
          VideoData.Status.SUCCESS -> "{Use Your VideoData (Video Duration, Video Frames as Bitmap, etc.)}")
      }
})
```
## Live - Memory Usage
Observes memory changes info.
```kotlin
 MemoryInfoLiveData(this)
                .observe(this, Observer { memoryInfo ->
                    when (memoryInfo) {
                        is LiveResult.LiveValue<MemoryInfoData> -> handleMemoryInfo(memoryInfo.value)
                    }

                })
```

### Dependency<br>
```
maven { url 'https://jitpack.io' }
```
```
dependencies {
    implementation 'com.github.savepopulation:live-tools:v1.0.1'
}
```

### Contributors:<br>

<a href="https://github.com/iammert">Mert Şimşek</a><br>
<a href="https://github.com/EfeBudak">Efe Budak</a><br>
<a href="https://github.com/savepopulation">Taylan Sabırcan</a><br>
<a href="https://github.com/aykuttasil">Aykut Asil</a><br>
<a href="https://github.com/ogulcanucarsu">Oğulcan Uçarsu</a><br>
<a href="https://github.com/tlgbltcn">Tolga Bolatcan</a><br>

### List of Apps using Live-Tools:
<a href="https://play.google.com/store/apps/details?id=com.raqun.labs">Phone Lab</a>

