package com.example.alldayfit.exercisestatus

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.alldayfit.exercisestatus.model.DailyEdit
import com.example.alldayfit.exercisestatus.model.DailyEdit.Companion.DIALOG_POSITION
import com.example.alldayfit.exercisestatus.model.DailyEdit.Companion.POST_POSITION

class ExerciseStatusAddGoalViewModel : ViewModel() {
    val dailyEditList = mutableListOf<DailyEdit>()
    val goalLiveData = MutableLiveData<List<DailyEdit>>()


    fun togglegoal(dailyEdit: DailyEdit) {
        dailyEdit.goalckeck = !dailyEdit.goalckeck
        goalLiveData.value = dailyEditList
    }


    fun setGoalList(dailyEdit: DailyEdit) {
        dailyEditList.add(dailyEdit)
        goalLiveData.value = dailyEditList
    }

    fun changeDialogType(dailyEdit: DailyEdit) {
        val updatedList = dailyEditList.map { dailyEdit ->
            if (dailyEdit.type == POST_POSITION) {
                dailyEdit.copy(type = DIALOG_POSITION)
            } else {
                dailyEdit
            }
        }
        goalLiveData.value = updatedList
    }

    fun changePostType(dailyEditList: List<DailyEdit>) {
        val updatedList = dailyEditList.map { dailyEdit ->
            if (dailyEdit.type == DIALOG_POSITION) {
                dailyEdit.copy(type = POST_POSITION)
            } else {
                dailyEdit
            }
        }
        goalLiveData.value = updatedList
    }


    fun deletegoal(dailyEdit: DailyEdit) {
        dailyEditList.remove(dailyEdit)
        goalLiveData.value = dailyEditList.toList()
    }
}