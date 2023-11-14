package com.example.alldayfit.calendar.anim

import com.example.alldayfit.R

val vertical = com.example.alldayfit.calendar.data.Animation(
    enter = R.anim.slide_in_up,
    exit = R.anim.fade_out,
    popEnter = R.anim.fade_in,
    popExit = R.anim.slide_out_down,
)

val horizontal = com.example.alldayfit.calendar.data.Animation(
    enter = R.anim.slide_in_right,
    exit = R.anim.slide_out_left,
    popEnter = R.anim.slide_in_left,
    popExit = R.anim.slide_out_right,
)