package com.example.alldayfit.dietrecord

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.alldayfit.main.MainViewModel

class DietRecordDialogViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DietRecordDialogViewModel::class.java)){
            return DietRecordDialogViewModel() as T
        }else{
            throw IllegalArgumentException("Not found view model class")
        }
    }
}