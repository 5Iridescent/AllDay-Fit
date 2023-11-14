package com.example.alldayfit.calendar.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

// View를 Visible 상태로 만드는 확장 함수입니다.
fun View.makeVisible() {
    visibility = View.VISIBLE
}

// View를 Invisible 상태로 만드는 확장 함수입니다.
fun View.makeInVisible() {
    visibility = View.INVISIBLE
}

// View를 Gone 상태로 만드는 확장 함수입니다.
fun View.makeGone() {
    visibility = View.GONE
}



// Context로부터 LayoutInflater를 가져오는 확장 프로퍼티입니다.
internal val Context.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(this)




// Context로부터 색상을 가져오는 함수입니다.
internal fun Context.getColorCompat(@ColorRes color: Int) =
    ContextCompat.getColor(this, color)

// TextView의 텍스트 색상을 리소스에서 가져온 색상으로 설정하는 확장 함수입니다.
internal fun TextView.setTextColorRes(@ColorRes color: Int) =
    setTextColor(context.getColorCompat(color))


// 주석을 해제하고 사용하면 StatusBar의 색상을 동적으로 변경할 수 있습니다.
//fun Fragment.addStatusBarColorUpdate(@ColorRes colorRes: Int) {
//    view?.findViewTreeLifecycleOwner()?.lifecycle?.addObserver(
//        StatusBarColorLifecycleObserver(
//            requireActivity(),
//            requireContext().getColorCompat(colorRes),
//        ),
//    )
//}
