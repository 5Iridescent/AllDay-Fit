package com.example.alldayfit.dietrecord

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alldayfit.dietrecord.model.DietRecordModel

class DietRecordViewModel : ViewModel(){

    private val _list: MutableLiveData<List<String>> = MutableLiveData()
    val list: LiveData<List<String>> get() = _list

    

}