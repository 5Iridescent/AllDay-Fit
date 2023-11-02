package com.example.alldayfit.community.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.alldayfit.R
import com.example.alldayfit.community.CommunityModel
import com.example.alldayfit.community.CommunityViewModel
import com.example.alldayfit.databinding.CommunityItemsBinding

class CommunityViewAdapter(private var viewModel: CommunityViewModel) :
    ListAdapter<CommunityModel, CommunityViewAdapter.HomeHolder>(diffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder {
        return HomeHolder(CommunityItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun onBindViewHolder(holder: CommunityViewAdapter.HomeHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class HomeHolder(val binding: CommunityItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CommunityModel) {
            Log.d("tahg","log")
            binding.apply {
                tvTitle.text = data.title
                tvInfo.text = data.post
                tvDate.text = "작성일 : ${data.date}"
                icUser.load(R.drawable.ic_user2)
                //임시로 아이콘 넣음
                tvNickname.text = "쫄튀"
                //임시 닉네임 넣기
            }
        }
    }

    fun setData(user: List<CommunityModel>) {
        Log.d("logdes",user.toString())
        super.submitList(user)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<CommunityModel>() {
            override fun areItemsTheSame(
                oldItem: CommunityModel,
                newItem: CommunityModel
            ): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(
                oldItem: CommunityModel,
                newItem: CommunityModel
            ): Boolean {
                return oldItem == newItem

            }
        }

    }
}
