package com.example.alldayfit.main.model

data class Goal(
    val goals: String,
    var goalckeck: Boolean = false,
    val type: Int
) {
    companion object {
        const val POST_POSITION = 0
        const val DIALOG_POSITION = 1
    }
}