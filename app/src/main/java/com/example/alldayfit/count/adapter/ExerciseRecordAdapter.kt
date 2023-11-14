package com.example.alldayfit.count.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.alldayfit.count.model.Count
import com.example.alldayfit.databinding.CountItemBinding

class ExerciseRecordAdapter() :
    ListAdapter<Count, ExerciseRecordAdapter.ViewHolder>(
        object : DiffUtil.ItemCallback<Count>() {
            override fun areItemsTheSame(oldItem: Count, newItem: Count): Boolean {
                return oldItem.date == newItem.date
            }

            override fun areContentsTheSame(oldItem: Count, newItem: Count): Boolean {
                return oldItem == newItem
            }

        }
    ) {

    class ViewHolder(private val binding: CountItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Count) {
            binding.exerciseTitele.text = item.name
            if (item.set == 0 && item.count == 0) {
                binding.set.text = ""
            } else if (item.set == 0 && item.count != 0) {
                binding.set.text = "${item.count} m"
            } else {
                binding.set.text = "${item.set}세트 ${item.count}회"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CountItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}






