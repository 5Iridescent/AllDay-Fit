package com.example.alldayfit.community

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.alldayfit.databinding.CommunityNewpostDialogBinding
import com.example.alldayfit.exercisestatus.BodyStatusViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class CommunityNewPostDialog(private var viewModel: CommunityViewModel): DialogFragment() {
    private var _binding: CommunityNewpostDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var comment :CommunityModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            CommunityNewpostDialogBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.btnClose.setOnClickListener {
            dismiss()
        }
        binding.btnWrite.setOnClickListener {
            val currentDate = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault()).format(Date())
           comment = CommunityModel(binding.etTitle.text.toString(),binding.etInfo.text.toString(),currentDate)
            viewModel.addcomment(comment)
            dismiss()
        }
        return (view)
    }
}