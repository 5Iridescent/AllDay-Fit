package com.example.alldayfit.community

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class CommunityViewModelFactory() : ViewModelProvider.Factory {
//    private val repositoryImpl : RealTimeRepository = RealTimeRepositoryImpl()

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommunityViewModel::class.java)) {
            return CommunityViewModel() as T
        } else {
            throw IllegalArgumentException("Not found view model class")
        }
    }
}
