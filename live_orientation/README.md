# Live Orientation
Live Orientation is a Live Data extended sensor based library. You can observe your device's orientation changes.
<br>

### How To Use

```
OrientationLiveData(this)
      .observe(this, Observer {
           it?.let {
               Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show()
           }
      })
```
