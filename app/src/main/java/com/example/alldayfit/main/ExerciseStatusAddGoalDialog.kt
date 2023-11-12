package com.example.alldayfit.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alldayfit.databinding.MainAddGoalDialogBinding
import com.example.alldayfit.main.adapter.GoalAdapter
import com.example.alldayfit.main.model.Goal

class ExerciseStatusAddGoalDialog : DialogFragment() {

    private var _binding: MainAddGoalDialogBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(
            this,
            MainViewModelFactory()
        )[MainViewModel::class.java]
    }
    private val adapter: GoalAdapter by lazy { GoalAdapter(viewModel) }
    private lateinit var goal: Goal

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //데이터 바인딩 연결
        _binding = MainAddGoalDialogBinding.inflate(inflater, container, false)
        initView()
        initViewModel()
        return binding.root
    }

    /* dialog design, data 초기 설정 */
    private fun initView() = with(binding) {
        goalListview.adapter = adapter
        binding.goalListview.layoutManager = LinearLayoutManager(context)
    }

    private fun initViewModel() = with(viewModel) {
        goalLiveData.observe(viewLifecycleOwner) { data ->
            adapter.addGoal(data)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() = with(binding) {
        // exercise 주간 목표 마치는 버튼
        finishBtn.setOnClickListener {
            goal = Goal(binding.goalEdit.text.toString(), false, Goal.POST_POSITION)
            viewModel.changeDialogType(goal)
            dismiss()
        }
        // dialog 닫는 버튼
        closeBtn.setOnClickListener {
            dismiss()
        }
        addGoal.setOnClickListener {
            if (goalEdit.text.isNullOrEmpty()) {
                return@setOnClickListener
            }
            goal = Goal(goalEdit.text.toString(), false, Goal.POST_POSITION)
            viewModel.setGoalList(goal)
            goalEdit.text.clear()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}