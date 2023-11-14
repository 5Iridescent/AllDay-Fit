package com.example.alldayfit.community


//import com.example.alldayfit.db.RealTimeRepositoryImpl
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

    private var communityEditlist = mutableListOf<CommunityPostEntity>()

    //추가와 데이터 제거를 위한 리스트
    var communityLivedata = MutableLiveData<List<CommunityPostEntity>>()

    //home 라이브 데이터
    var communitymydata = MutableLiveData<List<CommunityPostEntity>>()

    //mypost 라이브 데이터
    var selectedCommunityModel = MutableLiveData<CommunityPostEntity>()

    //확인 데이터를 보기 위한 라이브데이터
    private var changeCommet = MutableLiveData<CommunityPostEntity>()




    fun addcomment(entity: CommunityPostEntity) {
        communityEditlist.add(entity)
        communityLivedata.value = communityEditlist
//        repo.addPost(entity)
    }
    //코멘트를 작성하였을 때

    fun currentDate(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    }

    //현재 날짜 주입
    private fun deletecomment(entity: CommunityPostEntity) {
        communityEditlist.remove(entity)
        communityLivedata.value = communityEditlist
        communitymydata.value = communityEditlist
//        repo.removePost(entity)
    }

    //코멘트를 지웠을 때
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
        val updatedCommunityModel = changeCommet.value // 현재 선택된 CommunityModel을 가져옵니다.
        updatedCommunityModel?.let { model ->
            // 리스트를 수정 가능한 상태로 가져옵니다.
            val currentList = communitymydata.value?.toMutableList()
            currentList?.let { list ->
                // 특정 포지션에 있는 값을 업데이트합니다.
                if (list.size > position) {
                    list[position] = model
                    // 업데이트된 리스트로 MutableLiveData를 갱신합니다.
                    communitymydata.value = list
                }
            }
        }
    }

    private var selectedPosition: Int = -1

    // 선택된 위치를 설정하는 함수
    fun setSelectedPosition(position: Int) {
        selectedPosition = position
    }

    // 선택된 위치를 반환하는 함수
    fun getSelectedPosition(): Int {
        return selectedPosition
    }
}


