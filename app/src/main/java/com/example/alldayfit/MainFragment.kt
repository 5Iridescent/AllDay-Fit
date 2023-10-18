package com.example.alldayfit

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.alldayfit.databinding.FragmentMainBinding
import com.example.alldayfit.databinding.MainFragmentBinding
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
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
        binding.yearDate?.text = date
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


}