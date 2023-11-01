package com.example.alldayfit.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModel
import com.example.alldayfit.db.RealTimeRepository
import com.example.alldayfit.db.RealTimeRepositoryImpl

class MainViewModel : ViewModel() {
    val realtimeDB : RealTimeRepository = RealTimeRepositoryImpl()
    val goalList = mutableListOf<Goal>()
    val goalLiveData = MutableLiveData<List<Goal>>()


    fun togglegoal(goal: Goal) {
        goal.goalckeck = !goal.goalckeck
        goalLiveData.value = goalList
    private val _exerciseBtnTxt: MutableLiveData<Int> = MutableLiveData()
    val exerciseBtnTxt: LiveData<Int> get() = _exerciseBtnTxt

    fun updateExerciseTime(){
    // ViewModel 초기 값 설정
    init {
        _exerciseBtnTxt.value = R.string.exercise_start_txt
    }


    fun toggleExerciseBtn() {
        val currentTxt = exerciseBtnTxt.value
        if (currentTxt == R.string.exercise_start_txt) {
            _exerciseBtnTxt.value = R.string.exercise_finish_txt
            startTime = getCurrentLocalTime()
        } else {
            _exerciseBtnTxt.value = R.string.exercise_start_txt
            endTime = getCurrentLocalTime()
            val elapsedTime =
                elapsedTimeInMinutes(startTime.toZonedDateTime(), endTime.toZonedDateTime())
        }
    }
    }


    fun setGoalList(goal: Goal) {
        goalList.add(goal)
        goalLiveData.value = goalList
    }

    fun changeDialogType(goal: Goal) {
        val updatedList = goalList.map { goal ->
            if (goal.type == Goal.POST_POSITION) {
                goal.copy(type = Goal.DIALOG_POSITION)
            } else {
                goal
            }
        }
        goalLiveData.value = updatedList
    }

    fun changePostType(goachangelist: List<Goal>) {
        val updatedList = goachangelist.map { goal ->
            if (goal.type == Goal.DIALOG_POSITION) {
                goal.copy(type = Goal.POST_POSITION)
            } else {
                goal
            }
        }
        goalLiveData.value = updatedList
    }


    fun deletegoal(goal: Goal) {
        goalList.remove(goal)
        goalLiveData.value = goalList.toList()
    }
}
