package com.example.alldayfit.community

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.alldayfit.databinding.CommunityPostDialogBinding

class CommunityPostDialog(private var viewModel: CommunityViewModel) : DialogFragment() {
    private var _binding: CommunityPostDialogBinding? = null
    private val binding get() = _binding!!


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            CommunityPostDialogBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel.selectedCommunityModel.observe(viewLifecycleOwner) { comment ->
            binding.etInfo.text = comment.content
            binding.tvTitle.text = comment.title
            binding.tvDate.text = "작성일 :${comment.postingDate}"
        }
        exit()
        return (view)
    }

    private fun exit() = with(binding) {
        btnClose.setOnClickListener {
            dismiss()
        }
    }

}