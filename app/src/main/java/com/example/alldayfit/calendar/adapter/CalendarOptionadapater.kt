package com.example.alldayfit.calendar.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alldayfit.R
import com.example.alldayfit.calendar.CalendarFragment
import com.example.alldayfit.calendar.anim.horizontal
import com.example.alldayfit.calendar.data.ExampleItem
import com.example.alldayfit.calendar.util.layoutInflater
import com.example.alldayfit.databinding.CalendarViewOptionsItemViewBinding


class CalendarViewOptionsAdapter(val onClick: (ExampleItem) -> Unit) :
    RecyclerView.Adapter<CalendarViewOptionsAdapter.HomeOptionsViewHolder>() {

    val examples = listOf(

        ExampleItem(
            R.string.calendar_title,
            R.string.calendar_subtitle,
            horizontal,
        ) { CalendarFragment() },

        )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeOptionsViewHolder {
        return HomeOptionsViewHolder(
            CalendarViewOptionsItemViewBinding.inflate(
                parent.context.layoutInflater,
                parent,
                false,
            ),
        )
    }

    override fun onBindViewHolder(viewHolder: HomeOptionsViewHolder, position: Int) {
        viewHolder.bind(examples[position])
    }

    override fun getItemCount(): Int = examples.size

    inner class HomeOptionsViewHolder(private val binding: CalendarViewOptionsItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                onClick(examples[bindingAdapterPosition])
            }
        }

        fun bind(item: ExampleItem) {
            val context = itemView.context
            binding.itemOptionTitle.text = context.getString(item.titleRes)
            binding.itemOptionSubtitle.text = context.getString(item.subtitleRes)
        }
    }
}