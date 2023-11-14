package com.example.alldayfit.count

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.alldayfit.databinding.CountAddDialogBinding

class CountDialog : DialogFragment() {
    private var _binding: CountAddDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CountViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CountAddDialogBinding.inflate(inflater, container, false)
        return binding.root
    }
//      스피너 아이템
//    var exercisedata = resources.getStringArray(R.array.exercise_list)


}