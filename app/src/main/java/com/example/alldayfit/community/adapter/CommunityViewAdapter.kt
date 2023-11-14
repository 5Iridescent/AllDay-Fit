package com.example.alldayfit.community.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.alldayfit.R
import com.example.alldayfit.community.CommunityPostDialog
import com.example.alldayfit.community.CommunityViewModel
import com.example.alldayfit.community.model.CommunityPostEntity
import com.example.alldayfit.databinding.CommunityItemsBinding

class CommunityViewAdapter(
    private var viewModel: CommunityViewModel,
    private var fragmentManager: FragmentManager
) :
    ListAdapter<CommunityPostEntity, CommunityViewAdapter.HomeHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder {
        return HomeHolder(
            CommunityItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CommunityViewAdapter.HomeHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class HomeHolder(val binding: CommunityItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.communityContent.setOnClickListener {
                val data = currentList[adapterPosition] // 현재 항목에 대한 데이터를 가져옵니다.
                val communityPostDialog = CommunityPostDialog(viewModel)
                viewModel.onContentClicked(data)
                communityPostDialog.show(fragmentManager, "communityPostDialog")
            }

        }

        fun bind(data: CommunityPostEntity) {
            binding.apply {
                tvTitle.text = data.title
                tvInfo.text = data.content
                tvDate.text = "작성일 : ${data.postingDate}"
                icUser.load(R.drawable.ic_user2)
                //임시로 아이콘 넣음
                tvNickname.text = "쫄튀"
                //임시 닉네임 넣기
                btnAmen.visibility = View.GONE
                btnDelete.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

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
                return oldItem.postId == newItem.postId
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
