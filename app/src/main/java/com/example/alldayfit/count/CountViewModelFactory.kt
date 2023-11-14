package com.example.alldayfit.count

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.alldayfit.db.RealTimeRepositoryImpl
import com.example.alldayfit.main.MainViewModel

class CountViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CountViewModel::class.java)){
            return CountViewModel() as T
        }else{
            throw IllegalArgumentException("Not found view model class")
        }
    }
}