package com.example.alldayfit.count


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alldayfit.R
import com.example.alldayfit.databinding.CountPageActivityBinding
import com.example.alldayfit.main.MainFragment


class CountPage : AppCompatActivity() {
    private lateinit var mBinding: CountPageActivityBinding
    private var timerRunning = false
    private var lastClickTime: Long = 0

//    private var rainbow = GradientDrawable(
//        GradientDrawable.Orientation.TL_BR,
//        intArrayOf(
//            Color.RED,
//            Color.MAGENTA,
//            Color.BLUE,
//            Color.CYAN,
//            Color.GREEN,
//            Color.YELLOW,
//            Color.RED,
//        )
//    )

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

            if (clickTime - lastClickTime <= DOUBLE_CLICK_TIME_DELTA) {
                // 더블클릭 시 화면에 TextView 표시
                mBinding.finishTextView.visibility = View.VISIBLE
                mBinding.timer.visibility = View.INVISIBLE
                // 스톱워치 종료 후 다시 클릭해도 반응 없게하는 코드
                mBinding.count.isEnabled = false
                mBinding.count.setImageResource(R.drawable.circle_orange_back_shape)
                mBinding.rest.visibility = View.GONE
                mBinding.setRootine.visibility = View.GONE
                mBinding.btnMoreExercise.visibility = View.VISIBLE
                mBinding.btnFinishExercise.visibility = View.VISIBLE
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

        // 운동 더하기 버튼 클릭 시 스톱워치 초기 화면으로 이동
        mBinding.btnMoreExercise.setOnClickListener {
            val intent = Intent(this, CountPage::class.java)
            startActivity(intent)
        }

        // 운동 끝내기 버튼 클릭 시 메인화면으로 이동
        mBinding.btnFinishExercise.setOnClickListener {
            val fagmentManager = supportFragmentManager
            val fragmentTrasaction = fagmentManager.beginTransaction()

            val mainFragment = MainFragment()
            fragmentTrasaction.replace(R.id.main_fragment, mainFragment)

            fragmentTrasaction.addToBackStack(null)
            fragmentTrasaction.commit()
        }


    }

    private fun clickTimer() {
        if (!timerRunning) {
            startTimer()
            mBinding.textView.visibility = View.GONE
            mBinding.setRootine.visibility = View.INVISIBLE
            mBinding.timer.visibility = View.VISIBLE
        } else {
            mBinding.rest.visibility = View.VISIBLE
            mBinding.count.setImageResource(R.drawable.circle_orange_back_shape)
//            stopTimer()
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
        private const val DOUBLE_CLICK_TIME_DELTA: Long = 300 // 더블클릭 간격
    }

}