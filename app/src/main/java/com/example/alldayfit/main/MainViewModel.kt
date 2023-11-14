package com.example.alldayfit.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alldayfit.db.RealTimeRepository
import com.example.alldayfit.db.model.FirebaseModel
import com.example.alldayfit.main.model.Goal
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime

class MainViewModel(private val database: RealTimeRepository) : ViewModel() {
    private val _goal: MutableList<Goal> = mutableListOf()
    private val _goalList: MutableLiveData<List<Goal>> = MutableLiveData()

    private val _exerciseBtnTxt: MutableLiveData<Boolean> = MutableLiveData()
    val exerciseBtnTxt: LiveData<Boolean> get() = _exerciseBtnTxt

    private lateinit var exerciseData: FirebaseModel.ExerciseRecord
    private lateinit var startTime: ZonedDateTime
    private lateinit var endTime: ZonedDateTime

    // ViewModel 초기 값 설정
    init {
        _exerciseBtnTxt.value = false
        _goalList.value = _goal
    }

    fun toggleExerciseBtn() {
        val currentTxt = exerciseBtnTxt.value
        if (currentTxt == true) {
            _exerciseBtnTxt.value = false
            startTime = getCurrentLocalTime()
        }
        if (currentTxt == false) {
            _exerciseBtnTxt.value = true
            endTime = getCurrentLocalTime()
        }
    }

    fun updateExerciseData() {
        val elapsedTime = elapsedTimeInMinutes(startTime, endTime)
        Log.d("test", elapsedTime.toString())
        if (elapsedTime != null) {
            if (elapsedTime < 30) {
                return
            }
            exerciseData = FirebaseModel.ExerciseRecord(
                totalTime = elapsedTime, logDate = getCurrentLocalTime().toLogFormat()
            )
            database.addExercise(exerciseData)
        }
    }

    /* zonDatetime 형식으로 다시 반환 */
    private fun ZonedDateTime.toLogFormat(): String {
        return this.toLocalDate().toString()
    }

    /* ZonedDateTime 라이브러리로 현지 시각 구하기 */
    private fun getCurrentLocalTime(): ZonedDateTime {
        // 현지 시스템 지역 ID를 가지고 ZonedDateTime에 넣으면 현지 시각을 구할 수 있다.
        return ZonedDateTime.now(ZoneId.systemDefault())
    }

    // 시작 시간과 끝 시간 차이 비교
    private fun elapsedTimeInMinutes(startTime: ZonedDateTime, endTime: ZonedDateTime): Long? {
        // 시작 시간의 지역과 끝 시각의 지역이 다르면 null로
        if (startTime.zone != endTime.zone) {
            return null
        }
        // time라이브러리 duration로 통해서 시간 비교
        val duration = Duration.between(startTime, endTime)
        // 분으로 반환
        return duration.toMinutes()
    }


//    fun setGoalList(goal: Goal) {
//        _goal.add(goal)
//        updateGoal()
//    }
//
//    private fun updateGoal(){
//        _goalList.value = _goal.toList()
//    }
//
//    fun changeDialogType(goal: Goal) {
//        val updatedList = _goalList.map { goal ->
//            if (goal.type == Goal.POST_POSITION) {
//                goal.copy(type = Goal.DIALOG_POSITION)
//            } else {
//                goal
//            }
//        }
//        goalLiveData.value = updatedList
//        updateGoal()
//    }
//
//    fun changePostType(goachangelist: List<Goal>) {
//        val updatedList = goachangelist.map { goal ->
//            if (goal.type == Goal.DIALOG_POSITION) {
//                goal.copy(type = Goal.POST_POSITION)
//            } else {
//                goal
//            }
//        }
//        goalLiveData.value = updatedList
//        updateGoal()
//    }
//
//    fun deletegoal(goal: Goal, position: Int) {
//        _goalList.(goal)
//        goalLiveData.value = _goalList.toList()
//        updateGoal()
//    }

}
