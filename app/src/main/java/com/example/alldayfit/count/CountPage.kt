package com.example.alldayfit.count

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Chronometer
import com.example.alldayfit.databinding.CountPageActivityBinding


class CountPage : AppCompatActivity() {
    private lateinit var mBinding: CountPageActivityBinding
    private var timerRunning = false
    private var lastClickTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = CountPageActivityBinding.inflate(layoutInflater)
        val view = mBinding.root
        setContentView(view)

        mBinding.setRootine.setOnClickListener {
            val countDialog = CountDialog()
            countDialog.show(supportFragmentManager, "CountDialogFragment")
        }

        mBinding.count.setOnClickListener {
            clickTimer()
            val clickTime = System.currentTimeMillis()
            if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                // 더블클릭 시 화면에 TextView 표시
                mBinding.finishTextView.visibility = View.VISIBLE
                mBinding.timer.visibility = View.INVISIBLE
            }
            lastClickTime = clickTime
        }

        //시간을 00:00:00 으로 보이게 하는 코드
        mBinding.timer.setOnChronometerTickListener {
            val elapsedMillis = SystemClock.elapsedRealtime() - it.base
            val hours = (elapsedMillis / 3600000).toInt()
            val minutes = (elapsedMillis - hours * 3600000) / 60000
            val seconds = (elapsedMillis - hours * 3600000 - minutes * 60000) / 1000

            val formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            it.text = formattedTime
        }
    }

    private fun clickTimer() {
        if (!timerRunning) {
            startTimer()
            mBinding.textView.visibility = View.GONE
            mBinding.timer.visibility = View.VISIBLE
        } else {
            stopTimer()
        }
        timerRunning = !timerRunning
    }

    private fun startTimer() {
        mBinding.timer.base = SystemClock.elapsedRealtime()
        mBinding.timer.start()
    }

    private fun stopTimer() {
        mBinding.timer.stop()
    }

    companion object {
        private const val DOUBLE_CLICK_TIME_DELTA: Long = 300 // 더블클릭 간격 (밀리초)
    }
}