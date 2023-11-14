package com.example.alldayfit.exercisestatus

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alldayfit.exercisestatus.model.BodyStatusEdit

class BodyStatusViewModel : ViewModel() {

    private val _bodyStatus = MutableLiveData<BodyStatusEdit>()
    val bodyStatus: LiveData<BodyStatusEdit>
        get() = _bodyStatus

    init {
        _bodyStatus.value = BodyStatusEdit(23, 213)
    }

    fun updateBodyStatus(height: Int, weight: Int) {
        _bodyStatus.value = BodyStatusEdit(weight, height)
    }

    fun getHeight(): Int {
        return _bodyStatus.value?.height ?: 0
    }

    fun getWeight(): Int {
        return this._bodyStatus.value?.weight ?: 0
    }
}