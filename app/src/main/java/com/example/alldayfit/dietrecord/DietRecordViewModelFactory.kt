package com.example.alldayfit.dietrecord

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DietRecordViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DietRecordViewModel::class.java)) {
            return DietRecordViewModel() as T
        } else {
            throw IllegalArgumentException("Not found view model class")
        }
    }
}