package com.example.alldayfit.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.alldayfit.R
import com.example.alldayfit.count.CountActivity
import com.example.alldayfit.databinding.MainFragmentBinding
import com.example.alldayfit.databinding.MainWeeklyRecordItemBinding
import com.example.alldayfit.main.adapter.GoalAdapter
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class MainFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(
            this,
            MainViewModelFactory()
        )[MainViewModel::class.java]
    }
    private val goalAdapter by lazy { GoalAdapter() }
    private val days: List<MainWeeklyRecordItemBinding> by lazy {
        listOf(
            binding.sun,
            binding.mon,
            binding.tue,
            binding.wed,
            binding.thu,
            binding.fri,
            binding.sat
        )
    }
    private val weekDays: List<Int> = listOf(
        R.string.sunday,
        R.string.monday,
        R.string.tuesday,
        R.string.wednesday,
        R.string.thursday,
        R.string.friday,
        R.string.saturday
    )
    private val today: ZonedDateTime = ZonedDateTime.now(ZoneId.systemDefault())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        initView()
        initViewModel()
        return binding.root
    }

    /* fragment design, data 초기 설정 */
    private fun initView() = with(binding) {
        yearDateTxt.text = today.toDateFormat(MONTH_FORMAT)
        val startDay = getStartDay()
        val currentWeek = today.dayOfWeek.value % 7
        for (i in days.indices) {
            val day = startDay.plusDays(i.toLong())
            val textColor =
                if (i == currentWeek) R.color.white else if (i == 0) R.color.red else if (i == 6) R.color.navy else R.color.black
            val backgroundColor = if (i == currentWeek) R.color.dark_blue else R.color.white
            days[i].apply {
                weekTxt.text = getString(weekDays[i])
                dayWrap.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        backgroundColor
                    )
                )
                dayTxt.text = getString(R.string.today, day.toDateFormat(DAY_FORMAT))
                dayTxt.setTextColor(ContextCompat.getColor(requireContext(), textColor))
                weekTxt.setTextColor(ContextCompat.getColor(requireContext(), textColor))
            }
        }
        weekGoalList.adapter = goalAdapter
    }

    private fun initViewModel() = with(viewModel) {
        exerciseBtnTxt.observe(viewLifecycleOwner) {
            if (it) {
                binding.exerciseBtn.text = getString(R.string.exercise_finish)
            } else {
                binding.exerciseBtn.text = getString(R.string.exercise_start)
            }
        }
    }

    /* ZonedDateTime을 원하는 형식으로 변경 */
    private fun ZonedDateTime.toDateFormat(format: String): String {
        val formatter = DateTimeFormatter.ofPattern(format)
        return this.format(formatter)
    }

    private fun getStartDay(): ZonedDateTime {
        // 현재 날짜의 요일을 구합니다.
        val currentDayOfWeek = today.dayOfWeek
        // 주의 시작 날짜를 계산합니다. (월요일을 시작으로 한 주)
        return today.minusDays(currentDayOfWeek.value.toLong())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() = with(binding) {
        exerciseBtn.setOnClickListener {
            viewModel.toggleExerciseBtn()
            if (exerciseBtn.text == getString(R.string.exercise_finish)) {
                val intent = Intent(context, CountActivity::class.java)
                startActivity(intent)
            } else {
                viewModel.updateExerciseData()
            }
        }
        weekGoalFixBtn.setOnClickListener {
            showDialog(MainFragmentDirections.actionMainFragmentToExerciseStatusAddGoalDialog())
        }
    }

    private fun showDialog(action: NavDirections) {
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val DAY_FORMAT = "dd"
        const val MONTH_FORMAT = "yyyy.MM"
    }

}