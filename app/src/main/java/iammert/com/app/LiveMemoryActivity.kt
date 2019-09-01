package iammert.com.app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.raqun.live.core.LiveResult
import com.raqun.live.live.memory.MemoryInfoData
import com.raqun.live.live.memory.MemoryInfoLiveData
import kotlinx.android.synthetic.main.activity_memory.*

class LiveMemoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory)

        MemoryInfoLiveData(this)
                .observe(this, Observer { memoryInfo ->
                    when (memoryInfo) {
                        is LiveResult.LiveValue<MemoryInfoData> -> handleMemoryInfo(memoryInfo.value)
                    }

                })
    }

    @SuppressLint("SetTextI18n")
    private fun handleMemoryInfo(memoryInfo: MemoryInfoData?) {

        memoryUsage.text = " ${memoryInfo?.percentAvailableRounded} %"
        memoryProgress.progress = memoryInfo?.percentAvailableRounded ?: 0
    }
}