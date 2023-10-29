package com.example.alldayfit.exercisestatus;

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.alldayfit.R
import com.example.alldayfit.databinding.ExerciseStatusAddGoalDialogBinding
import com.example.alldayfit.exercisestatus.adapter.DailyAdapter
import com.example.alldayfit.exercisestatus.model.DailyEdit

class ExerciseStatusAddGoalDialog(private val exerciseStatusAddGoalViewModel: ExerciseStatusAddGoalViewModel) :
    DialogFragment() {

    private var _binding: ExerciseStatusAddGoalDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: DailyAdapter
    private lateinit var goal: DailyEdit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //데이터 바인딩 연결
        _binding =
            ExerciseStatusAddGoalDialogBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.goalListview.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(context)
        adapter = DailyAdapter(exerciseStatusAddGoalViewModel)
        binding.goalListview.adapter = adapter

        binding.addGoal.setOnClickListener {
            goal = DailyEdit(binding.goalEdit.text.toString(), false, DailyEdit.POST_POSITION)
            exerciseStatusAddGoalViewModel.setGoalList(goal)
        }
        exerciseStatusAddGoalViewModel.goalLiveData.observe(
            viewLifecycleOwner, Observer { data ->
                adapter.addGoal(data)
            })
        initView()
        return view
    }

    /* dialog design, data 초기 설정 */
    private fun initView() = with(binding) {
        //dialog background 색 변경 xml에서는 적용이 안 되어서 java에서 적용
//        root.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.none))
        // exercise 주간 목표 마치는 버튼
        finishBtn.setOnClickListener {
            goal = DailyEdit(binding.goalEdit.text.toString(), false, DailyEdit.POST_POSITION)
            exerciseStatusAddGoalViewModel.changeDialogType(goal)
            dismiss()
        }
        // dialog 닫는 버튼
        closeBtn.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}