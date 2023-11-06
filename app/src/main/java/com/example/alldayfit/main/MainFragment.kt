package com.example.alldayfit.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.alldayfit.R
import com.example.alldayfit.community.model.CommunityPostEntity
import com.example.alldayfit.count.CountPage
import com.example.alldayfit.databinding.MainFragmentBinding
import com.example.alldayfit.databinding.MainWeeklyRecordItemBinding
import com.example.alldayfit.db.RealTimeRepository
import com.example.alldayfit.db.RealTimeRepositoryImpl
import com.example.alldayfit.db.model.FirebaseModel
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

    private val wow = RealTimeRepositoryImpl.getInstance()

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
        // 이번주 요일 text 설정
        for (i in days) {
            i.weekTxt.text = getString(weekDays[days.indexOf(i)])
        }
        // 이번 달 month text 설정
        yearDateTxt.text = today.toDateFormat(MONTH_FORMAT)
        val startDay = getStartDay()
        for (i in days.indices) {
            val day = startDay.plusDays(i.toLong())
            days[i].dayTxt.text = getString(R.string.today, day.toDateFormat(DAY_FORMAT))
        }
        val currentDay = today.dayOfWeek.value % 7
        for (i in days.indices) {
            val dayView = days[i]
            val textColor = if (i == currentDay) R.color.white else R.color.black
            val backgroundColor = if (i == currentDay) R.color.blue else R.color.white
            with(dayView) {
                dayWrap.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        backgroundColor
                    )
                )
                dayTxt.setTextColor(ContextCompat.getColor(requireContext(), textColor))
                weekTxt.setTextColor(ContextCompat.getColor(requireContext(), textColor))
            }
        }
    }

    private fun initViewModel() {
        with(viewModel) {
            exerciseBtnTxt.observe(viewLifecycleOwner) { it ->
                binding.exerciseBtn.text = getString(it)
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
            val intent = Intent(context, CountPage::class.java)
            startActivity(intent)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
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