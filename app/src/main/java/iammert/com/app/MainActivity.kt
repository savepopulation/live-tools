package iammert.com.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        liveBatteryButton.setOnClickListener {
            val intent = Intent(this@MainActivity, LiveBatteryActivity::class.java)
            startActivity(intent)
        }

        liveLocationButton.setOnClickListener {
            val intent = Intent(this@MainActivity, LiveLocationActivity::class.java)
            startActivity(intent)
        }

        liveMemoryButton.setOnClickListener {
            val intent = Intent(this@MainActivity, LiveMemoryActivity::class.java)
            startActivity(intent)
        }

        liveOrientationButton.setOnClickListener {
            val intent = Intent(this@MainActivity, LiveOrientationActivitty::class.java)
            startActivity(intent)
        }

    }

}
