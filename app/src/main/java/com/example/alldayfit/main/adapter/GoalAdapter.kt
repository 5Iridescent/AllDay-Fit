package com.example.alldayfit.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.alldayfit.databinding.DietRecordAddItemBinding
import com.example.alldayfit.databinding.MainGoalItemBinding
import com.example.alldayfit.main.Goal
import com.example.alldayfit.main.MainViewModel
import java.lang.RuntimeException

class GoalAdapter(private val viewModel: MainViewModel) :
    ListAdapter<Goal, RecyclerView.ViewHolder>(diffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            Goal.POST_POSITION -> {
                val binding = DietRecordAddItemBinding.inflate(layoutInflater, parent, false)
                PostHolder(binding)
            }

            Goal.DIALOG_POSITION -> {
                val binding = MainGoalItemBinding.inflate(layoutInflater, parent, false)
                DailyHolder(binding)
            }

            else -> throw RuntimeException("Error")
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = getItem(position)
        when(currentItem.type){
            Goal.POST_POSITION -> (holder as PostHolder).bind(currentItem)
            Goal.DIALOG_POSITION -> (holder as DailyHolder).bind(currentItem)
        }

    }
    inner class DailyHolder(val binding: MainGoalItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Goal) {
            binding.apply {
                goal.setText(data.goals)
                checkbox.isChecked = data.goalckeck
            }
        }
    }

    inner class PostHolder(val binding: DietRecordAddItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Goal) {
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

    fun addGoal(newData: List<Goal>) {
        super.submitList(newData)
        notifyDataSetChanged()
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Goal>() {
            override fun areItemsTheSame(oldItem: Goal, newItem: Goal): Boolean {
                return oldItem.goals == newItem.goals
            }

            override fun areContentsTheSame(oldItem: Goal, newItem: Goal): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }
}

