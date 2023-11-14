package com.example.alldayfit.exercisestatus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.alldayfit.R
import com.example.alldayfit.databinding.ExerciseStatusFragmentBinding


class ExerciseStatusFragment : Fragment() {

    private var _binding: ExerciseStatusFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: BodyStatusViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ExerciseStatusFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[BodyStatusViewModel::class.java]
        initView()
        initViewModel()
        return binding.root
    }

    private fun initView() = with(binding) {
    }

    private fun initViewModel() = with(viewModel) {
        bodyStatus.observe(viewLifecycleOwner) { bodyStatus ->
            binding.statusWeightView.text = getString(R.string.weight_value, bodyStatus.weight)
            binding.statusHeightView.text = getString(R.string.height_value, bodyStatus.height)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() = with(binding) {
        fixed.setOnClickListener {
            showDialog(ExerciseStatusFragmentDirections.actionExerciseStatusFragmentToExerciseStatusDailyEditDialog())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showDialog(action: NavDirections) {
        findNavController().navigate(action)
    }

    companion object {
    }
}