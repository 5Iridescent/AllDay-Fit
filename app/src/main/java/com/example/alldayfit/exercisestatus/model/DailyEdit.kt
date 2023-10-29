package com.example.alldayfit.exercisestatus.model

data class DailyEdit(
    val goals: String,
    var goalckeck : Boolean = false,
    val type : Int
) {
    companion object {
        const val POST_POSITION = 0
        const val DIALOG_POSITION = 1
    }
}
