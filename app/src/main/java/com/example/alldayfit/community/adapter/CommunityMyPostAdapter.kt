package com.example.alldayfit.community.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.alldayfit.R
import com.example.alldayfit.community.CommunityEditDialog
import com.example.alldayfit.community.CommunityViewModel
import com.example.alldayfit.community.model.CommunityPostEntity
import com.example.alldayfit.databinding.CommunityItemsBinding

class CommunityMyPostAdapter(
    private var viewModel: CommunityViewModel,
    private var fragmentManager: FragmentManager
) :
    ListAdapter<CommunityPostEntity, CommunityMyPostAdapter.MyPostHolder>(diffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPostHolder {
        val view = CommunityItemsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyPostHolder(view)
    }


    override fun onBindViewHolder(holder: CommunityMyPostAdapter.MyPostHolder, position: Int) {
        val data = currentList[position]
        holder.bind(data)
    }



    inner class MyPostHolder(val binding: CommunityItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnAmen.setOnClickListener {
                val data = currentList[bindingAdapterPosition] // 현재 항목에 대한 데이터를 가져옵니다.
                val communityEditDialog = CommunityEditDialog(viewModel)
                viewModel.onContentClicked(data)
                communityEditDialog.show(fragmentManager, "communityEditDialog")
                viewModel.setSelectedPosition(bindingAdapterPosition)
            }

        }


        @SuppressLint("SetTextI18n")
        fun bind(data: CommunityPostEntity) {
            binding.apply {
                tvTitle.text = data.title
                tvInfo.text = data.content
                tvDate.text = "작성일 : ${data.postingDate}"
                icUser.load(R.drawable.ic_user2)
                tvNickname.text = "쫄튀"
                btnAmen.visibility = View.VISIBLE
                btnDelete.visibility = View.VISIBLE
            }
            binding.btnDelete.setOnClickListener {
                val context = binding.root.context
                viewModel.cancelDialog(context, data)
            }
        }

    }


    override fun getItemCount(): Int {
        return currentList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(user: List<CommunityPostEntity>) {
        notifyDataSetChanged()
        super.submitList(user)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<CommunityPostEntity>() {
            override fun areItemsTheSame(
                oldItem: CommunityPostEntity,
                newItem: CommunityPostEntity
            ): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(
                oldItem: CommunityPostEntity,
                newItem: CommunityPostEntity
            ): Boolean {
                return oldItem.content == newItem.content

            }
        }

    }
}


