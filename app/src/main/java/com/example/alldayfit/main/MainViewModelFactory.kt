package com.example.alldayfit.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.alldayfit.db.RealTimeRepositoryImpl


class MainViewModelFactory : ViewModelProvider.Factory {
    private val realTimeDB = RealTimeRepositoryImpl.getInstance()
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(realTimeDB) as T
        } else {
            throw IllegalArgumentException("Not found view model class")
        }
    }
}