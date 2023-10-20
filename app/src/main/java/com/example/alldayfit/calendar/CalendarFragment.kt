package com.example.alldayfit.calendar

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.children
import com.example.alldayfit.R
import com.example.alldayfit.calendar.util.makeInVisible
import com.example.alldayfit.calendar.util.makeVisible
import com.example.alldayfit.calendar.util.setTextColorRes
import com.example.alldayfit.databinding.CalendarDayBinding
import com.example.alldayfit.databinding.CalendarFragmentBinding
import com.example.alldayfit.databinding.CalendarHeaderBinding
import com.google.android.material.snackbar.Snackbar
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.kizitonwose.calendar.view.ViewContainer
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.Menu
//import android.view.MenuInflater
//import android.view.MenuItem
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.appcompat.widget.Toolbar
//import androidx.core.view.children
//import androidx.fragment.app.Fragment
//import com.example.alldayfit.R
//import com.example.alldayfit.calendar.Util.makeInVisible
//import com.example.alldayfit.calendar.Util.makeVisible
//import com.example.alldayfit.calendar.Util.setTextColorRes
//import com.example.alldayfit.calendar.binder.CalendarDayBinder
//import com.example.alldayfit.databinding.CalendarDayBinding
//import com.example.alldayfit.databinding.CalendarFragmentBinding
//import com.example.alldayfit.databinding.CalendarHeaderBinding
//import com.google.android.material.snackbar.Snackbar
//import com.kizitonwose.calendar.core.CalendarDay
//import com.kizitonwose.calendar.core.CalendarMonth
//import com.kizitonwose.calendar.core.DayPosition
//import com.kizitonwose.calendar.core.daysOfWeek
//import com.kizitonwose.calendar.core.yearMonth
//import com.kizitonwose.calendar.view.MonthDayBinder
//import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
//import com.kizitonwose.calendar.view.ViewContainer
//import java.time.DayOfWeek
//import java.time.LocalDate
//import java.time.YearMonth
//import java.time.format.DateTimeFormatter

//class CalendarFragment : Fragment() {
//
//    private lateinit var binding: CalendarFragmentBinding
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = CalendarFragmentBinding.inflate(inflater, container, false)
//        val view = binding.root
//
//        setupCalendar()
//
//        return view
//    }
//
//    private fun setupCalendar() {
//        val today = LocalDate.now()
//        binding.exTwoHeaderText.text = "${today.yearMonth.month.name} ${today.year}"
//
//        binding.CalendarView.setup(
//            YearMonth.now(),
//            YearMonth.now().plusMonths(12),
//            DayOfWeek.SUNDAY
//        )
//
//        // 여기에서 날짜와 이벤트 데이터를 설정해줘야 합니다.
//        val eventDays: List<CalendarDay> = // 날짜와 이벤트 데이터를 설정
//
//        val dayBinder = CalendarDayBinder { selectedDate ->
//            // 날짜를 클릭했을 때의 동작을 처리합니다.
//        }
//
//        binding.CalendarView.dayBinder = dayBinder
//        binding.CalendarView.monthHeaderBinder = MonthHeaderFooterBinder { header ->
//            // 월 헤더에 대한 바인딩 코드를 작성합니다.
//        }
//
//        binding.monthBack.setOnClickListener {
//            binding.CalendarView.findFirstVisibleMonth()?.let {
//                binding.CalendarView.smoothScrollToMonth(it.yearMonth.minusMonths(1))
//            }
//        }
//
//        binding.monthNext.setOnClickListener {
//            binding.CalendarView.findFirstVisibleMonth()?.let {
//                binding.CalendarView.smoothScrollToMonth(it.yearMonth.plusMonths(1))
//            }
//        }
//    }
//}
//
class  CalendarFragment : BaseFragment(R.layout.calendar_fragment), HasToolbar, HasBackButton {

    override val toolbar: Toolbar
        get() = binding.exTwoToolbar

    override val titleRes: Int = R.string.example_2_title

    private lateinit var binding: CalendarFragmentBinding

    private var selectedDate: LocalDate? = null
    private val today = LocalDate.now()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding = CalendarFragmentBinding.bind(view)
        val daysOfWeek = daysOfWeek()
        binding.legendLayout.root.children.forEachIndexed { index, child ->
            (child as TextView).apply {
                text = daysOfWeek[index].name.first().toString()
                setTextColorRes(R.color.white)
            }
        }

        configureBinders()
        binding.exTwoCalendar.setup(
            YearMonth.now(),
            YearMonth.now().plusMonths(200),
            daysOfWeek.first(),
        )
    }

    private lateinit var menuItem: MenuItem
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.calendar_menu, menu)
        menuItem = menu.getItem(0)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuItemDone) {
            val date = selectedDate ?: return false
            val text = "Selected: ${DateTimeFormatter.ofPattern("d MMMM yyyy").format(date)}"
            Snackbar.make(requireView(), text, Snackbar.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun configureBinders() {
        val calendarView = binding.exTwoCalendar

        class DayViewContainer(view: View) : ViewContainer(view) {
            // Will be set when this container is bound. See the dayBinder.
            lateinit var day: CalendarDay
            val textView = CalendarDayBinding.bind(view).exTwoDayText

            init {
                textView.setOnClickListener {
                    if (day.position == DayPosition.MonthDate) {
                        if (selectedDate == day.date) {
                            selectedDate = null
                            calendarView.notifyDayChanged(day)
                        } else {
                            val oldDate = selectedDate
                            selectedDate = day.date
                            calendarView.notifyDateChanged(day.date)
                            oldDate?.let { calendarView.notifyDateChanged(oldDate) }
                        }
                        menuItem.isVisible = selectedDate != null
                    }
                }
            }
        }

        calendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, data: CalendarDay) {
                container.day = data
                val textView = container.textView
                textView.text = data.date.dayOfMonth.toString()

                if (data.position == DayPosition.MonthDate) {
                    textView.makeVisible()
                    when (data.date) {
                        selectedDate -> {
                            textView.setTextColorRes(R.color.white)
                            textView.setBackgroundResource(R.drawable.example_2_selected_bg)
                        }
                        today -> {
                            textView.setTextColorRes(R.color.red)
                            textView.background = null
                        }
                        else -> {
                            textView.setTextColorRes(R.color.black)
                            textView.background = null
                        }
                    }
                } else {
                    textView.makeInVisible()
                }
            }
        }

        class MonthViewContainer(view: View) : ViewContainer(view) {
            val textView = CalendarHeaderBinding.bind(view).exTwoHeaderText
        }
        calendarView.monthHeaderBinder =
            object : MonthHeaderFooterBinder<MonthViewContainer> {
                override fun create(view: View) = MonthViewContainer(view)
                override fun bind(container: MonthViewContainer, data: CalendarMonth) {
                    val displayText = "${data.yearMonth.year} - ${data.yearMonth.monthValue}"
                    container.textView.text = displayText
                }
            }
}


    companion object{
        fun newInstance() = CalendarFragment()
    }
}
