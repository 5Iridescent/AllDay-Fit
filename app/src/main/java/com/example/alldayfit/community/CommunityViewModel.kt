package com.example.alldayfit.community


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class CommunityViewModel : ViewModel() {

    var communityEditlist = mutableListOf<CommunityModel>()
    var communityLivedata = MutableLiveData<List<CommunityModel>>()

    fun currentDate(): String {
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        return currentDate
    }

    fun addcomment(communityModel: CommunityModel) {
        communityEditlist.add(communityModel)
        communityLivedata.value = communityEditlist

    }
}