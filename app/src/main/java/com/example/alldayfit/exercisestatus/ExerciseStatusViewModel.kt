package com.example.alldayfit.exercisestatus

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ExerciseStatusViewModel : ViewModel() {

    private val dailyEditList = mutableListOf<DailyEdit>()
    val goalLiveData = MutableLiveData<List<DailyEdit>>()

    fun togglegoal(dailyEdit: DailyEdit) {
        dailyEdit.goalckeck = !dailyEdit.goalckeck
        goalLiveData.value = dailyEditList
    }
    fun updateGoalList(newGoalList: List<DailyEdit>) {
        goalLiveData.value = dailyEditList
    }
        fun setGoalList(input : String) {
            val currentList = goalLiveData.value ?: emptyList() // 현재 목록 가져오기, 없으면 빈 목록
            val newGoal = DailyEdit(goals = input) // 새 DailyEdit 객체 생성
            val updatedList = currentList.toMutableList()
            updatedList.add(newGoal) // 목록에 새 목표 추가
            goalLiveData.value = updatedList
            dailyEditList.add(newGoal)
        }
    fun deletegoal(dailyEdit: DailyEdit) {
        dailyEditList.remove(dailyEdit)
        goalLiveData.value = dailyEditList.toList()
    }
}