package com.example.alldayfit.calendar


import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.alldayfit.R
import com.example.alldayfit.calendar.util.makeGone
import com.example.alldayfit.calendar.util.makeVisible


// 툴바를 가지고 있는 Fragment에서 구현해야 하는 인터페이스입니다.
interface HasToolbar {
    val toolbar: Toolbar? // 툴바를 사용하지 않을 때는 null을 반환합니다.
}

// 뒤로 가기 버튼을 가지고 있는 Fragment에서 구현해야 하는 인터페이스입니다.
interface HasBackButton

// 공통 로직을 처리하기 위한 추상 Fragment 클래스입니다.
abstract class BaseFragment(@LayoutRes layoutRes: Int) : Fragment(layoutRes) {

    // Fragment에서 사용할 툴바의 제목 리소스 ID를 반환합니다.
    abstract val titleRes: Int?

    // Activity의 툴바를 가져오기 위한 프로퍼티입니다.
    val activityToolbar: Toolbar
        get() = (requireActivity() as CalendarActivity).binding.activityToolbar

    override fun onStart() {
        super.onStart()
        // HasToolbar를 구현한 Fragment의 경우, Activity의 툴바를 설정하고 숨깁니다.
        if (this is HasToolbar) {
            activityToolbar.makeGone()
            (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        }

        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        // HasBackButton을 구현한 Fragment의 경우, ActionBar에 제목을 설정하고 뒤로 가기 버튼을 활성화합니다.
        if (this is HasBackButton) {
            actionBar?.title = if (titleRes != null) context?.getString(titleRes!!) else ""
            actionBar?.setDisplayHomeAsUpEnabled(true)
        } else {
            actionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }

    override fun onStop() {
        super.onStop()
        // HasToolbar를 구현한 Fragment의 경우, Activity의 툴바를 다시 보이게 합니다.
        if (this is HasToolbar) {
            activityToolbar.makeVisible()
            (requireActivity() as AppCompatActivity).setSupportActionBar(activityToolbar)
        }

        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        // HasBackButton을 구현한 Fragment의 경우, ActionBar의 제목을 변경하고 뒤로 가기 버튼을 활성화합니다.
        if (this is HasBackButton) {
            actionBar?.title = context?.getString(R.string.activity_title_view)
        }
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
