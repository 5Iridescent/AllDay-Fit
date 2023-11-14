package com.example.alldayfit.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.alldayfit.community.model.CommunityPostEntity
import com.example.alldayfit.databinding.CommunityNewpostDialogBinding


class CommunityNewPostDialog(private var viewModel: CommunityViewModel) : DialogFragment() {
    private var _binding: CommunityNewpostDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var comment: CommunityPostEntity
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            CommunityNewpostDialogBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.btnWrite.setOnClickListener {
            if (binding.etTitle.text.toString().isEmpty() or binding.etInfo.text.toString()
                    .isEmpty()
            ) {
                Toast.makeText(requireContext(), "내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                comment = CommunityPostEntity(
                    "NTqKsmydkVOE9fWxxTolGDSkL7p2",
                    binding.etTitle.text.toString(),
                    viewModel.currentDate(),
                    "Temporary nk",
                    binding.etInfo.text.toString()
                )
                viewModel.addcomment(comment)
                dismiss()
            }
        }
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        exit()
        return view
    }

    private fun exit() = with(binding) {
        btnClose.setOnClickListener {
            dismiss()
        }
        btnCancel.setOnClickListener {
            dismiss()
        }
    }
}