package com.example.alldayfit.exercisestatus

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.alldayfit.R
import com.example.alldayfit.databinding.ExerciseStatusDailyEditDialogBinding

class BodyStatusDialog: DialogFragment() {
    private var _binding: ExerciseStatusDailyEditDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: BodyStatusViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ExerciseStatusDailyEditDialogBinding.inflate(inflater, container, false)
        initView()


        binding.closeBtn.setOnClickListener {
            dismiss()
        }

        viewModel = ViewModelProvider(requireActivity()).get(BodyStatusViewModel::class.java)

        binding.correctionComplete.setOnClickListener {
            Log.d("BodyStatusDialogFragment", "Edit Button Clicked")
            val weight = binding.statusWeightView.statusMetricsInputTxt.text.toString()
            val height = binding.statusHeightView.statusMetricsInputTxt.text.toString()

            val statusEdit = BodyStatusEdit(weight, height)
            // ViewModel을 통해 데이터 업데이트
            viewModel.setBodyStatus(statusEdit)
            dismiss()
        }




//        val view = inflater.inflate(R.layout.exercise_status_daily_edit_dialog, container, false)
//        // 다이얼로그 크기 조절
//        dialog?.window?.setLayout(380, 530)
//        return view

        return binding.root


    }

    private fun initView() = with(binding) {
        statusWeightView.statusTypeTxt.text = getString(R.string.weight)
        statusHeightView.statusTypeTxt.text = getString(R.string.height)
        statusBmiView.statusTypeTxt.text = getString(R.string.bmi)
        statusExerciseTimeView.statusTypeTxt.text = getString(R.string.exercise_time)
        statusCalorieConsumptionView.statusTypeTxt.text = getString(R.string.calorie_consumption)
        statusCalorieConsumptionView.statusTypeTxt.textSize = 13F
    }
}