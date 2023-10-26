package com.example.alldayfit.exercisestatus

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.alldayfit.R
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.alldayfit.databinding.ExerciseStatusFragmentBinding
import com.example.alldayfit.main.MainFragment


class ExerciseStatusFragment : Fragment() {

    private var _binding: ExerciseStatusFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    val exerciseStatusViewModel: ExerciseStatusViewModel by viewModels()
    private lateinit var adapter: DailyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ExerciseStatusFragmentBinding.inflate(inflater, container, false)
        initView()

        binding.goalListFixBtn.setOnClickListener {
            val addGoalDialog =
                ExerciseStatusDailyEditDialog(exerciseStatusViewModel)
            addGoalDialog.show(childFragmentManager, "ExerciseStatusDailyEditDialog")
            exerciseStatusViewModel.changePostType(exerciseStatusViewModel.dailyEditList)
        }//프래그먼트 띄우기 및 타입 변환

        binding.goalList.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(context)
        adapter = DailyAdapter(exerciseStatusViewModel)
        binding.goalList.adapter = adapter
        exerciseStatusViewModel.goalLiveData.observe(viewLifecycleOwner, Observer { data ->
            adapter.addGoal(data)
        })
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