package com.example.alldayfit.count


import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alldayfit.R
import com.example.alldayfit.count.adapter.ExerciseRecordAdapter
import com.example.alldayfit.count.model.Count
import com.example.alldayfit.databinding.CountActivityBinding
import java.time.ZoneId
import java.time.ZonedDateTime


class CountActivity : AppCompatActivity() {
    private lateinit var binding: CountActivityBinding
    private lateinit var countDownTimer: CountDownTimer

    private val viewModel: CountViewModel by lazy {
        ViewModelProvider(
            this,
            CountViewModelFactory()
        )[CountViewModel::class.java]
    }

    private val adapter by lazy { ExerciseRecordAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CountActivityBinding.inflate(layoutInflater)
        initView()
        initViewModel()
        setContentView(binding.root)
    }

    private fun initViewModel() = with(viewModel) {
        isCounting.observe(this@CountActivity) {
            if (isRunning.value == true) {
                updateStartPauseButton(it)
            }
        }
        isRunning.observe(this@CountActivity) {
            toggleVisibility(it)
        }
        routine.observe(this@CountActivity) {
            showRoutine(it)
            adapter.submitList(it)
        }
    }

    private fun showRoutine(routine: List<Count>) = with(binding) {
        if (routine.isEmpty()) {
            routineSetBtn.visibility = View.VISIBLE
            routineList.visibility = View.INVISIBLE
        } else {
            routineSetBtn.visibility = View.GONE
            routineList.visibility = View.VISIBLE
        }
    }

    private fun toggleVisibility(isRunning: Boolean) = with(binding) {
        if (isRunning) {
            routineSetBtn.visibility = View.GONE
            textView.visibility = View.INVISIBLE
            timer.visibility = View.VISIBLE
        } else {
            textView.visibility = View.VISIBLE
            timer.visibility = View.INVISIBLE
            rest.visibility = View.INVISIBLE
        }
    }

    private fun contextGetColor(color: Int): Int {
        return ContextCompat.getColor(this@CountActivity, color)
    }

    private fun updateStartPauseButton(isCounting: Boolean) = with(binding) {
        if (isCounting) {
            rest.visibility = View.INVISIBLE
            startBtn.apply {
                text = getString(R.string.rest)
                background = AppCompatResources.getDrawable(
                    this@CountActivity,
                    R.drawable.btn_circle_orange_counter
                )
            }
            progressbar.apply {
                progressTintList =
                    ColorStateList.valueOf(contextGetColor(R.color.blue))
                progress = progressbar.max
            }
            timer.apply {
                setTextColor(contextGetColor(R.color.blue))
                base = SystemClock.elapsedRealtime()
                start()
            }
        } else {
            rest.visibility = View.VISIBLE
            startBtn.apply {
                text = getString(R.string.start)
                background = AppCompatResources.getDrawable(
                    this@CountActivity,
                    R.drawable.btn_circle_blue_counter
                )
            }
            progressbar.progressTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this@CountActivity, R.color.orange))
            timer.apply {
                setTextColor(contextGetColor(R.color.orange))
                stop()
            }
            startCountDown()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initView() = with(binding) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        routineList.layoutManager = LinearLayoutManager(this@CountActivity)
        routineList.adapter = adapter
        timer.setOnChronometerTickListener {
            val elapsedMillis = SystemClock.elapsedRealtime() - it.base
            val hours = (elapsedMillis / 3600000).toInt()
            val minutes = (elapsedMillis - hours * 3600000) / 60000
            val seconds = (elapsedMillis - hours * 3600000 - minutes * 60000) / 1000
            val formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            it.text = formattedTime
        }
        progressbar.progressTintList = ColorStateList.valueOf(contextGetColor(R.color.blue))
        routineSetBtn.setOnClickListener {
            val countDialog = CountDialog()
            countDialog.show(supportFragmentManager, "CountDialogFragment")
        }
        startBtn.setOnClickListener {
            if (startBtn.text == getString(R.string.rest)) {
                viewModel.addRoutine(
                    Count(
                        ZonedDateTime.now(ZoneId.systemDefault()),
                        binding.timer.text.toString()
                    )
                )
            }
            viewModel.onSetButtonClick()
        }
        endBtn.setOnClickListener {
            viewModel.onEndButtonClick()
            viewModel.clearRoutine()
            finishView()
            intArrayOf(
                Color.RED,
                Color.YELLOW,
                Color.GREEN,
                Color.CYAN,
                Color.BLUE,
                Color.MAGENTA,
                Color.RED
            )
        }

        moreExerciseBtn.setOnClickListener {
            viewModel.stopwatchMode()
            recreate()
        }
        finishExerciseBtn.setOnClickListener {
            finish()
        }
    }

    private fun startCountDown() {

        binding.startBtn.apply {
            isEnabled = false
            alpha = 0.3f
        }
        countDownTimer = object : CountDownTimer(46000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (viewModel.isRunning.value == false) {
                    onFinish()
                    return
                }
                val formattedTime = formatTime(millisUntilFinished)
                val progress = millisUntilFinished.toInt() - 1000
                binding.timer.text = formattedTime
                binding.progressbar.progress = progress
                binding.startBtn.isEnabled = false
            }

            override fun onFinish() {
                binding.startBtn.performClick()
                binding.startBtn.apply {
                    isEnabled = true
                    alpha = 1.0f
                }
                binding.progressbar.progress = binding.progressbar.max
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

    private fun finishView() {
        binding.textView.text = getString(R.string.count_finish_ment)
        binding.endBtn.isEnabled = false
        binding.timer.stop()
        binding.startBtn.isEnabled = false
        binding.moreExerciseBtn.visibility = View.VISIBLE
        binding.finishExerciseBtn.visibility = View.VISIBLE
    }


}
