package iammert.com.app

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.aykuttasil.livelocation.LocationLiveData
import com.raqun.live_orientation.OrientationLiveData

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        OrientationLiveData(this)
                .observe(this, Observer {
                    it?.let {
                        //Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show()
                    }
                })

        LocationLiveData(this).observe(this, Observer {
            it?.let {
                Toast.makeText(this, it.provider, Toast.LENGTH_SHORT).show()
            }
        })


    }
}
