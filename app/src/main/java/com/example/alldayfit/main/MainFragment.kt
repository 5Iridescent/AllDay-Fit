package com.example.alldayfit.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.alldayfit.count.CountPage
import com.example.alldayfit.databinding.MainFragmentBinding
import com.example.alldayfit.db.model.FirebaseModel
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(
            this,
            MainViewModelFactory()
        )[MainViewModel::class.java]
    }
    lateinit var selectedDate: LocalDate
    val cal: Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        initView()
        initViewModel()
        return binding.root
    }

    private fun initView() = with(binding) {
        doExercise.setOnClickListener{
            viewModel.toggleExerciseBtn()
        }
    }

    private fun initViewModel() {
        with(viewModel) {
            exerciseBtnTxt.observe(viewLifecycleOwner) { it ->
                binding.doExercise.text = getString(it)
            }
        }
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
            binding.sun,
            binding.mon,
            binding.tue,
            binding.wed,
            binding.thu,
            binding.fri,
            binding.sat
        )
        for (i in 0 until 7) {
            cal.add(Calendar.DAY_OF_MONTH, (i + 1 - cal.get(Calendar.DAY_OF_WEEK)))
            val date = cal.time
            val formattedDate = dateDay(date)
            val dayView = daysOfWeek[i]
            dayView.text = formattedDate
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
        const val TIME_FORMAT = "yyyy.MM.dd.HH.mm"
        const val LOG_FORMAT = "yyyy.MM.dd"
    }


}