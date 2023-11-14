package com.example.alldayfit.count.model


import java.time.ZonedDateTime

data class Count(
    val date : ZonedDateTime,
    val name: String,
    val count: Int = 0,
    val set: Int = 0,
)
