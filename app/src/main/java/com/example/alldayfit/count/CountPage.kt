package com.example.alldayfit.count

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.Chronometer
import com.example.alldayfit.databinding.CountPageActivityBinding


class CountPage : AppCompatActivity() {
    private lateinit var mBinding: CountPageActivityBinding
    private var timer = false
    private lateinit var chronometer: Chronometer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = CountPageActivityBinding.inflate(layoutInflater)
        val view = mBinding.root
        setContentView(view)
        chronometer = mBinding.timer

        mBinding.setRootine.setOnClickListener {
            val countDialog = CountDialog()
            countDialog.show(supportFragmentManager, "CountDialogFragment")
        }

        mBinding.count.setOnClickListener {
            toggleStartWithClickVisibility()
            if(!timer) {
                startTimer()
            } else {
                stopTimer()
            }
        }
    }

    private fun toggleStartWithClickVisibility() {
        // startWithClick TextView의 visibility를 토글
        mBinding.textView.visibility = if (mBinding.textView.visibility == View.VISIBLE) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun startTimer() {
        mBinding.timer.base = SystemClock.elapsedRealtime()
        mBinding.timer.start()
        timer = true
    }

    private fun stopTimer() {
        mBinding.timer.stop()
        timer = false
    }
}