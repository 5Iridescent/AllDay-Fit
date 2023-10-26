package com.example.alldayfit.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.alldayfit.databinding.MainFragmentBinding
import com.example.alldayfit.db.RealTimeRepository
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale


class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    lateinit var selectedDate: LocalDate
    val cal: Calendar = Calendar.getInstance()

    private var startTime: String? = null
    private var endTime: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)

        //운동시작 완료 시간
        binding.doExercise.setOnClickListener {
            if (binding.doExercise.text == "운동하기") {
                binding.doExercise.text = "끝내기"
                startTime = SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(Date())
                val message = "운동시작 시간: $startTime"
                showToast(message)
            } else if (binding.doExercise.text == "끝내기") {
                binding.doExercise.text = "운동하기"
                endTime = SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(Date())
                val message = "운동완료 시간: $endTime"
                showToast(message)

                val elapsedTime = calculateElapsedTime(startTime!!, endTime!!)
                showToast(
                    "운동 시간: ${elapsedTime.get(Calendar.MINUTE)} 분" + " ${elapsedTime.get(Calendar.SECOND)} 초"
                )
            }
        }
        return binding.root
    }

    // 운동 시간 계산
    private fun calculateElapsedTime(startTime: String, endTime: String): Calendar {
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm:ss")
        try {
            val start = format.parse(startTime)
            val end = format.parse(endTime)
            if (start != null && end != null) {
                val startcalendar = Calendar.getInstance()
                startcalendar.time = start

                val endcalendar = Calendar.getInstance()
                endcalendar.time = end

                val elapsedTimeMillis = endcalendar.timeInMillis - startcalendar.timeInMillis

                val calendar = Calendar.getInstance()
                calendar.timeInMillis = elapsedTimeMillis

                return calendar
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Calendar.getInstance()
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDayView()
        doDayOfWeek()
    }

    private fun setDayView() {
        selectedDate = LocalDate.now()
        val monthformatter = DateTimeFormatter.ofPattern("yyyy.MM")
        val date = monthformatter.format(selectedDate).toString()
        binding.yearDate.text = date
        // 년도와 월 표시
    }

    private fun doDayOfWeek() {
        fun dateDay(date: Date): String {
            val dayFormat = SimpleDateFormat("dd일", Locale.getDefault())
            val day = dayFormat.format(date)
            return day
        }

        val daysOfWeek = listOf(
            _binding?.sun,
            _binding?.mon,
            _binding?.tue,
            _binding?.wed,
            _binding?.thu,
            _binding?.fri,
            _binding?.sat
        )
        for (i in 0 until 7) {
            cal.add(Calendar.DAY_OF_MONTH, (i + 1 - cal.get(Calendar.DAY_OF_WEEK)))
            val date = cal.time
            val formattedDate = dateDay(date)
            val dayView = daysOfWeek[i]
            dayView?.text = formattedDate
            cal.add(Calendar.DAY_OF_MONTH, 1)
        }
        //일주일 날짜 표기
        val daysToAdd = if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            0
        } else {
            7 - cal.get(Calendar.DAY_OF_WEEK) + 1
        }
        cal.add(Calendar.DAY_OF_MONTH, daysToAdd)
        //일요일이 지난 경우 한 주을 더해주기
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = MainFragment()
    }


}