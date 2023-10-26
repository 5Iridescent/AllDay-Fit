package com.example.alldayfit.exercisestatus

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.example.alldayfit.R
import com.example.alldayfit.databinding.ExerciseStatusAddGoalBinding
import com.example.alldayfit.databinding.ExerciseStatusDailyEditDialogBinding
import com.example.alldayfit.databinding.ExerciseStatusFragmentBinding
import com.example.alldayfit.exercisestatus.DailyEdit.Companion.DIALOG_POSITION
import com.example.alldayfit.exercisestatus.DailyEdit.Companion.POST_POSITION
import com.github.mikephil.charting.utils.Utils.init

class ExerciseStatusDailyEditDialog(private val exerciseStatusViewModel: ExerciseStatusViewModel) :
    DialogFragment() {

    private var _binding: ExerciseStatusAddGoalBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: DailyAdapter
    private lateinit var goal: DailyEdit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            ExerciseStatusAddGoalBinding.inflate(inflater, container, false)
        val view = binding.root
        //데이터 바인딩 연결


        binding.goalListview.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(context)
        adapter = DailyAdapter(exerciseStatusViewModel)
        binding.goalListview.adapter = adapter



        binding.addGoal.setOnClickListener {
            goal = DailyEdit(binding.goalEdit.text.toString(), false, POST_POSITION)
            exerciseStatusViewModel.setGoalList(goal)
        }
        exerciseStatusViewModel.goalLiveData.observe(
            viewLifecycleOwner,
            Observer { data ->
                adapter.addGoal(data)
            })


        binding.finishBtn.setOnClickListener {
            goal = DailyEdit(binding.goalEdit.text.toString(), false, POST_POSITION)
            exerciseStatusViewModel.changeDialogType(goal)
            dismiss()
        }
        return view
    }


}