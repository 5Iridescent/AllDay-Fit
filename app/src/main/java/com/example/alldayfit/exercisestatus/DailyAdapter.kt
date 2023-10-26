package com.example.alldayfit.exercisestatus

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.hardware.fingerprint.FingerprintManagerCompat.from
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.alldayfit.databinding.DietRecordAddItemBinding
import com.example.alldayfit.databinding.ExerciseStatusAddGoalBinding
import com.example.alldayfit.databinding.ExerciseStatusGoalItemBinding
import com.example.alldayfit.exercisestatus.DailyEdit.Companion.DIALOG_POSITION
import com.example.alldayfit.exercisestatus.DailyEdit.Companion.POST_POSITION
import java.lang.RuntimeException

class DailyAdapter(private val viewModel: ExerciseStatusViewModel) :
    ListAdapter<DailyEdit, RecyclerView.ViewHolder>(diffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            POST_POSITION -> {
                val binding = DietRecordAddItemBinding.inflate(layoutInflater, parent, false)
                PostHolder(binding)
            }

            DIALOG_POSITION -> {
                val binding = ExerciseStatusGoalItemBinding.inflate(layoutInflater, parent, false)
                DailyHolder(binding)
            }

            else -> throw RuntimeException("Error")
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = getItem(position)
        when(currentItem.type){
            POST_POSITION  -> (holder as PostHolder).bind(currentItem)
            DIALOG_POSITION -> (holder as DailyHolder).bind(currentItem)
        }

    }
    inner class DailyHolder(val binding: ExerciseStatusGoalItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DailyEdit) {
            binding.apply {
                goal.setText(data.goals)
                checkbox.isChecked = data.goalckeck
            }
        }
    }

    inner class PostHolder(val binding: DietRecordAddItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DailyEdit) {
            binding.apply {
                mealEdit.setText(data.goals)
            }
            binding.btnDelete.setOnClickListener {
                viewModel.deletegoal(data)
            }
        }
    }

    // 아이템 타입 리턴
    override fun getItemViewType(position: Int): Int {
        return getItem(position).type
    }

    fun addGoal(newData: List<DailyEdit>) {
        super.submitList(newData)
        notifyDataSetChanged()
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<DailyEdit>() {
            override fun areItemsTheSame(oldItem: DailyEdit, newItem: DailyEdit): Boolean {
                return oldItem.goals == newItem.goals
            }

            override fun areContentsTheSame(oldItem: DailyEdit, newItem: DailyEdit): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }
}


