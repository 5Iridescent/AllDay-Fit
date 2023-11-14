package com.example.alldayfit.count

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alldayfit.count.model.Count

class CountViewModel : ViewModel() {
    private val _isRunning = MutableLiveData<Boolean>()
    val isRunning: LiveData<Boolean> get() = _isRunning
    private val _isCounting = MutableLiveData<Boolean>()
    val isCounting: LiveData<Boolean> get() = _isCounting

    private val routineList: MutableList<Count> = mutableListOf()
    private val _routine = MutableLiveData<List<Count>>()
    val routine: LiveData<List<Count>> get() = _routine

    init {
        initViewModel()
    }

    private fun initViewModel() {
        _isRunning.value = false
        _isCounting.value = false
    }

    fun stopwatchMode() {
        _routine.value = listOf()
    }

    fun onSetButtonClick() {
        if (_isRunning.value == true) {
            _isCounting.value = !_isCounting.value!!
            if (_isCounting.value != true) return
        } else {
            _isRunning.value = !_isRunning.value!!
            _isCounting.value = !_isCounting.value!!
        }
    }

    fun addRoutine(routine: Count) {
        routineList.add(routine)
        _routine.value = routineList
    }

    fun clearRoutine() {
        routineList.clear()
        _routine.value = routineList
    }

    fun onEndButtonClick() {
        if (_isRunning.value == true) {
            initViewModel()
            clearRoutine()
        }
    }
}