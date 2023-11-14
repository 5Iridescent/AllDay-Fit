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
    val goalList: MutableLiveData<List<Goal>> get() = _goalList

    private val _exerciseBtnTxt: MutableLiveData<Boolean> = MutableLiveData()
    val exerciseBtnTxt: LiveData<Boolean> get() = _exerciseBtnTxt

    private lateinit var exerciseData: FirebaseModel.ExerciseRecord
    private lateinit var startTime: ZonedDateTime
    private lateinit var endTime: ZonedDateTime

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
                totalTime = elapsedTime ?: 30, logDate = getCurrentLocalTime().toLogFormat()
            )
            database.addExercise(exerciseData)
        }
    }

    private fun ZonedDateTime.toLogFormat(): String {
        return this.toLocalDate().toString()
    }

    private fun getCurrentLocalTime(): ZonedDateTime {
        return ZonedDateTime.now(ZoneId.systemDefault())
    }

    private fun elapsedTimeInMinutes(startTime: ZonedDateTime, endTime: ZonedDateTime): Long? {
        if (startTime.zone != endTime.zone) {
            return null
        }
        val duration = Duration.between(startTime, endTime)
        return duration.toMinutes()
    }

    private fun getWeekExercise() {

    }



}
