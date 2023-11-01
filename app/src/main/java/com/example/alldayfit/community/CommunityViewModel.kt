package com.example.alldayfit.community


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class CommunityViewModel : ViewModel() {

    var communityEditlist = mutableListOf<CommunityModel>()
    var communityLivedata = MutableLiveData<List<CommunityModel>>()


    fun addcomment(communityModel: CommunityModel) {
        communityEditlist.add(communityModel)
        communityLivedata.value = communityEditlist
    }

}