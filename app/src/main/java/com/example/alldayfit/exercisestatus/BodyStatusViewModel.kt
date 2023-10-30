package com.example.alldayfit.exercisestatus

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alldayfit.exercisestatus.model.DailyEdit

class BodyStatusViewModel : ViewModel() {

private val _bodyStatus = MutableLiveData<BodyStatusEdit>()
    val bodyStatus: LiveData<BodyStatusEdit>
        get() = _bodyStatus

    fun setBodyStatus(bodyStatusEdit: BodyStatusEdit) {
        _bodyStatus.value = bodyStatusEdit
    }
}