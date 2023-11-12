package com.example.alldayfit.count


import ExerciseRecordAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alldayfit.R
import com.example.alldayfit.databinding.CountPageActivityBinding
import com.example.alldayfit.main.MainFragment



class CountPage : AppCompatActivity() {
    private lateinit var mBinding: CountPageActivityBinding
    private var timerRunning = false
    private lateinit var countDownTimer: CountDownTimer

    //리사이클러뷰 및 어댑터 초기화
    val exerciseRecords = mutableListOf<ExerciseRecord>()
    val adapter = ExerciseRecordAdapter(exerciseRecords)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = CountPageActivityBinding.inflate(layoutInflater)
        val view = mBinding.root
        setContentView(view)

        // 어댑터 연결
        mBinding.rvCount.layoutManager = LinearLayoutManager(this)
        mBinding.rvCount.adapter = adapter

        mBinding.setRootine.setOnClickListener {
            val countDialog = CountDialog()
            countDialog.show(supportFragmentManager, "CountDialogFragment")
        }

        // 시작 버튼을 누르면 타이머 시작
        mBinding.btnStart.setOnClickListener {
            mBinding.btnRest.visibility = View.VISIBLE
            clickTimer()
        }

        // 휴식 버튼 눌렀을 때 카운트 다운 시작
        mBinding.btnRest.setOnClickListener {
            stopTimer()
            startCountDown()

            mBinding.rvCount.visibility = View.VISIBLE
            // 클릭한 순간의 타이머 값을 타임스탬프로 사용
            val exerciseRecord = ExerciseRecord(mBinding.timer.text.toString())
            // 리스트에 기록 추가
            exerciseRecords.add(exerciseRecord)
            // 어댑터에 데이터 변경 알림
            adapter.notifyDataSetChanged()
        }

        // 종료 버튼을 누르면 타이머 종료
        mBinding.btnFinish.setOnClickListener {
            saveRecyclerView()
            if(!timerRunning){
                saveRecyclerView()
                finishView()
            }
                mBinding.rvCount.visibility = View.VISIBLE
                finishView()
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

        // back 버튼 클릭 시 메인화면으로 이동
        mBinding.back.setOnClickListener {
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
            startCountDown()
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

    private fun startCountDown() {
        mBinding.rest.visibility = View.VISIBLE
        mBinding.progressbar.visibility = View.VISIBLE
        mBinding.count.visibility = View.INVISIBLE
        mBinding.rest.setTextColor(ContextCompat.getColor(this, R.color.orange))
        mBinding.timer.setTextColor(ContextCompat.getColor(this, R.color.orange))

        // countdown이 시작되면 버튼 비활성화
        mBinding.btnRest.isEnabled = false
        mBinding.btnStart.isEnabled = false

        countDownTimer = object : CountDownTimer(46000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // 남은 시간을 HH:mm:ss 형식으로 변환
                val formattedTime = formatTime(millisUntilFinished)
                mBinding.timer.text = formattedTime

                val progress = millisUntilFinished.toInt()
                mBinding.progressbar.progress = progress
            }

            override fun onFinish() {
                if(mBinding.finishTextView.visibility == View.VISIBLE){
                    return
                }
                startTimer()
                timerRunning = !timerRunning
                // countdown이 끝나면 버튼 다시 활성화
                mBinding.btnRest.isEnabled = true
                // 타이머가 완료되면 호출되는 메서드
                mBinding.timer.text = "00:00:00"
                mBinding.rest.visibility = View.INVISIBLE
                mBinding.count.setImageResource(R.drawable.circle_blue_back_shape)
                mBinding.timer.setTextColor(ContextCompat.getColor(this@CountPage, R.color.blue))

                mBinding.count.visibility = View.VISIBLE
                mBinding.progressbar.visibility = View.INVISIBLE
            }
        }
        countDownTimer.start()
    }

    private fun formatTime(millis: Long): String {
        val seconds = millis / 1000
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val remainingSeconds = seconds % 60

        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds)
    }

    private fun finishView(){
        mBinding.finishTextView.visibility = View.VISIBLE
        mBinding.timer.visibility = View.INVISIBLE
        mBinding.count.setImageResource(R.drawable.circle_blue_back_shape) //Todo 무지개 테두리로 변경

        // 스톱워치 종료 후 다시 클릭해도 반응 없게하는 코드
        mBinding.btnFinish.isEnabled = false
        mBinding.rest.visibility = View.GONE
        mBinding.btnMoreExercise.visibility = View.VISIBLE
        mBinding.btnFinishExercise.visibility = View.VISIBLE
        mBinding.progressbar.visibility = View.INVISIBLE
        mBinding.btnRest.visibility = View.INVISIBLE
        mBinding.btnRest.isEnabled = false
        mBinding.btnStart.isEnabled = false
        mBinding.btnStart.visibility = View.VISIBLE
        mBinding.count.visibility = View.VISIBLE
    }

    private fun saveRecyclerView(){
        mBinding.rvCount.visibility = View.VISIBLE
        // 종료할 때 마지막 타이머 기록 저장
        val exerciseRecord = ExerciseRecord(mBinding.timer.text.toString())
        exerciseRecords.add(exerciseRecord)
        adapter.notifyDataSetChanged()
    }
}
