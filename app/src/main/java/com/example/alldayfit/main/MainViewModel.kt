package com.example.alldayfit.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import com.example.alldayfit.R
import com.example.alldayfit.db.RealTimeRepository
import com.example.alldayfit.db.RealTimeRepositoryImpl
import com.example.alldayfit.db.model.FirebaseModel
import com.example.alldayfit.main.model.Goal
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class MainViewModel : ViewModel() {
    private val realtimeDB : RealTimeRepository = RealTimeRepositoryImpl()
    val goalList = mutableListOf<Goal>()
    val goalLiveData = MutableLiveData<List<Goal>>()

    private val _exerciseBtnTxt: MutableLiveData<Int> = MutableLiveData()
    val exerciseBtnTxt: LiveData<Int> get() = _exerciseBtnTxt

    private lateinit var exerciseData: FirebaseModel.ExerciseRecord
    private lateinit var startTime: ZonedDateTime
    private lateinit var endTime: ZonedDateTime

    fun togglegoal(goal: Goal) {
        goal.goalckeck = !goal.goalckeck
        goalLiveData.value = goalList
    }

    // ViewModel 초기 값 설정
    init {
        _exerciseBtnTxt.value = R.string.exercise_start
    }


    fun toggleExerciseBtn() {
        val currentTxt = exerciseBtnTxt.value
        if (currentTxt == R.string.exercise_start) {
            _exerciseBtnTxt.value = R.string.exercise_finish
            startTime = getCurrentLocalTime()
        } else {
            _exerciseBtnTxt.value = R.string.exercise_start
            endTime = getCurrentLocalTime()
            val elapsedTime =
                elapsedTimeInMinutes(startTime, endTime)
        }
    }

    private fun updateExerciseData(elapsedTime: Int) {
        exerciseData = FirebaseModel.ExerciseRecord(
            totalTime = elapsedTime, logDate = getCurrentLocalTime().toLogFormat()
        )
        realtimeDB.addExercise(exerciseData)
    }

    /* zonDatetime 형식으로 다시 반환 */
    private fun ZonedDateTime.toLogFormat(): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        return this.format(formatter)
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

    fun updateExerciseTime(exerciseData: FirebaseModel.ExerciseRecord) {

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
