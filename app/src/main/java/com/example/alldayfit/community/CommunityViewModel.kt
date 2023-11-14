package com.example.alldayfit.community


import android.content.Context
import android.view.LayoutInflater
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alldayfit.community.model.CommunityPostEntity
import com.example.alldayfit.databinding.CommunityCancleDialogBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class CommunityViewModel : ViewModel() {

    var communityEditlist = mutableListOf<CommunityPostEntity>()

    var communityLivedata = MutableLiveData<List<CommunityPostEntity>>()

    var communitymydata = MutableLiveData<List<CommunityPostEntity>>()

    var selectedCommunityModel = MutableLiveData<CommunityPostEntity>()

    var changeCommet = MutableLiveData<CommunityPostEntity>()

    private var onDataChangedCallback: ((List<CommunityPostEntity>) -> Unit)? = null


    fun addcomment(entity: CommunityPostEntity) {
        communityEditlist.add(entity)
        communityLivedata.value = communityEditlist
    }

    fun currentDate(): String {
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        return currentDate
    }

    fun deletecomment(entity: CommunityPostEntity) {
        communityEditlist.remove(entity)
        communityLivedata.value = communityEditlist
        communitymydata.value = communityEditlist
    }

    fun cancelDialog(context: Context, entity: CommunityPostEntity) {
        val binding = CommunityCancleDialogBinding.inflate(LayoutInflater.from(context))
        val dialog = MaterialAlertDialogBuilder(context)
            .setView(binding.root)
            .create()

        binding.btnDelete.setOnClickListener {
            deletecomment(entity)
            dialog.dismiss()
        }
        binding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    fun onContentClicked(entity: CommunityPostEntity) {
        selectedCommunityModel.value = entity
    }

    fun changeComment(entity: CommunityPostEntity) {
        changeCommet.value = entity
    }

    fun updateItemInCommunityList(position: Int) {
        val updatedCommunityModel = changeCommet.value
        updatedCommunityModel?.let { model ->
            val currentList = communitymydata.value?.toMutableList()
            currentList?.let { list ->
                if (list.size > position) {
                    list[position] = model
                    communitymydata.value = list
                }
            }
        }
    }

    private var selectedPosition: Int = -1

    fun setSelectedPosition(position: Int) {
        selectedPosition = position
    }

    fun getSelectedPosition(): Int {
        return selectedPosition
    }
}


