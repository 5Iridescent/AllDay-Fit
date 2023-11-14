package com.example.alldayfit.count

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alldayfit.count.model.ExerciseRecord
import com.example.alldayfit.databinding.CountItemBinding

class ExerciseRecordAdapter(private val records: List<ExerciseRecord>) :
    RecyclerView.Adapter<ExerciseRecordAdapter.ViewHolder>() {

    class ViewHolder(private val binding: CountItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(record: ExerciseRecord) {
            binding.exerciseTitele.text = record.timestamp

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
        val item = getItemId(position)
        holder.bind(records[position])
    }

    override fun getItemCount(): Int {
        return records.size
    }
}






