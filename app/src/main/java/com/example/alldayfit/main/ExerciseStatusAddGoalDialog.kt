package com.example.alldayfit.main;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.alldayfit.databinding.MainAddGoalDialogBinding

import com.example.alldayfit.main.adapter.GoalAdapter
import com.example.alldayfit.main.model.Goal

class ExerciseStatusAddGoalDialog(private val mainViewModel: MainViewModel) :
    DialogFragment() {

    private var _binding: MainAddGoalDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: GoalAdapter
    private lateinit var goal: Goal

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //데이터 바인딩 연결
        _binding =
                MainAddGoalDialogBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.goalListview.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(context)
        adapter = GoalAdapter(mainViewModel)
        binding.goalListview.adapter = adapter

        binding.addGoal.setOnClickListener {
            goal = Goal(binding.goalEdit.text.toString(), false, Goal.POST_POSITION)
            mainViewModel.setGoalList(goal)
        }
        mainViewModel.goalLiveData.observe(
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
            goal = Goal(binding.goalEdit.text.toString(), false, Goal.POST_POSITION)
           mainViewModel.changeDialogType(goal)
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