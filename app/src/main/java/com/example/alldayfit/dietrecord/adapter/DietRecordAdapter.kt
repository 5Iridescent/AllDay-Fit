package com.example.alldayfit.dietrecord.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alldayfit.R

class DietRecordAdapter(private val dietRecordsList: MutableList<String>) :
    RecyclerView.Adapter<DietRecordAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mealTextView: TextView = itemView.findViewById(R.id.meal_edit)
        val btnDelete: ImageButton = itemView.findViewById(R.id.btn_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.diet_record_add_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dietRecord = dietRecordsList[position]
        holder.mealTextView.text = dietRecord

        holder.btnDelete.setOnClickListener {
            dietRecordsList.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return dietRecordsList.size
    }
}