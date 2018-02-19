package com.raqun.sensitive

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.raqun.sensitiveactivity.DeviceOrientation
import com.raqun.sensitiveactivity.SensitiveActivity


class MainActivity : SensitiveActivity() {

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textview)
    }

    override fun onOrientationChanged(deviceOrientation: DeviceOrientation?) {
        textView.text = deviceOrientation?.name
    }
}
