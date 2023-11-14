package com.example.alldayfit.settings.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alldayfit.databinding.SettingNoticeItemBinding
import com.example.alldayfit.settings.ui.SettingNoticeItem

class SettingNoticeAdapter(private val context: Context) :
    RecyclerView.Adapter<SettingNoticeAdapter.Holder>() {

    private val items: MutableList<SettingNoticeItem> = mutableListOf()
    var itemsNotice = ArrayList<SettingNoticeItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            SettingNoticeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class Holder(val binding: SettingNoticeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

}