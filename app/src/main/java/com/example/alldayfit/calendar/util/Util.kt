package com.example.alldayfit.calendar.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeInVisible() {
    visibility = View.INVISIBLE
}

fun View.makeGone() {
    visibility = View.GONE
}



internal val Context.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(this)




internal fun Context.getColorCompat(@ColorRes color: Int) =
    ContextCompat.getColor(this, color)

internal fun TextView.setTextColorRes(@ColorRes color: Int) =
    setTextColor(context.getColorCompat(color))

