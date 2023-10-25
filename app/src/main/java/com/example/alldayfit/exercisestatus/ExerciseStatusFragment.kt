package com.example.alldayfit.exercisestatus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.alldayfit.R
import com.example.alldayfit.databinding.ExerciseStatusFragmentBinding
import com.example.alldayfit.main.MainFragment


class ExerciseStatusFragment : Fragment() {

    private var _binding: ExerciseStatusFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ExerciseStatusFragmentBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() = with(binding) {
        statusWeightView.statusTypeTxt.text=getString(R.string.weight)
        statusHeightView.statusTypeTxt.text=getString(R.string.height)
        statusBmiView.statusTypeTxt.text=getString(R.string.bmi)
        statusExerciseTimeView.statusTypeTxt.text=getString(R.string.exercise_time)
        statusCalorieConsumptionView.statusTypeTxt.text=getString(R.string.calorie_consumption)
        statusCalorieConsumptionView.statusTypeTxt.textSize = 13F
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = ExerciseStatusFragment()
    }
}