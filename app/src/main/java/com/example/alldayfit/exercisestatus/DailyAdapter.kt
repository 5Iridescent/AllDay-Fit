package com.example.alldayfit.exercisestatus

import android.text.Editable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alldayfit.R
import com.example.alldayfit.databinding.ExerciseStatusGoalItemBinding

class DailyAdapter (var goalList : List<DailyEdit>): RecyclerView.Adapter<DailyAdapter.DailyHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyHolder  {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.exercise_status_goal_item,parent,false)
        return  DailyHolder(ExerciseStatusGoalItemBinding.bind(view))
    }

    override fun onBindViewHolder(dailyHolder: DailyHolder, position: Int) {
        val dailyEdit = goalList[position]
        dailyHolder.bind(dailyEdit)
    }
    override fun getItemCount()= goalList.size
    inner class DailyHolder(val binding: ExerciseStatusGoalItemBinding) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(data : DailyEdit) {
            binding.goal.setText(data.goals)
            binding.checkbox.isChecked = data.goalckeck
        }
    }
    fun addGoal(newData: List<DailyEdit>) {
        goalList = newData
        notifyDataSetChanged()
    }
}