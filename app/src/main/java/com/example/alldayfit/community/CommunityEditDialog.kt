package com.example.alldayfit.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.example.alldayfit.community.model.CommunityPostEntity
import com.example.alldayfit.databinding.CommunityEditDialogBinding

class CommunityEditDialog(private var viewModel: CommunityViewModel) : DialogFragment() {

    private var _binding: CommunityEditDialogBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CommunityEditDialogBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel.selectedCommunityModel.observe(viewLifecycleOwner, Observer { comment ->
            binding.etInfo.setText(comment.content)
            binding.etTitle.setText(comment.title)
//            binding.tvDate.text = "작성일 :${comment.date}"
        })

        binding.btnWrite.setOnClickListener {
            if (binding.etTitle.text.toString().isEmpty() or binding.etInfo.text.toString()
                    .isEmpty()
            ) {
                Toast.makeText(requireContext(), "내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                val editComment = CommunityPostEntity(
                    "NTqKsmydkVOE9fWxxTolGDSkL7p2",
                    binding.etTitle.text.toString(),
                    viewModel.currentDate(),
                    "Temporary nickname",
                    binding.etInfo.text.toString()
                )
                viewModel.changeComment(editComment)
                val newPostion = viewModel.getSelectedPosition()
                viewModel.updateItemInCommunityList(newPostion)
                //커뮤니티 데이터 추가
                dismiss()
            }
        }

        exit()
        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun exit() = with(binding) {
        btnClose.setOnClickListener {
            dismiss()
        }
        btnCancel.setOnClickListener {
            dismiss()
        }
    }
}