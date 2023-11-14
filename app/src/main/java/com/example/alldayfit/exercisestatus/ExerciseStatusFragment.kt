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

    // Todo viewmodel 초기화 방법 lateinit by lazy & factory 만들어서 veiwmodle 실행
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

    /* fragment design, data 초기 설정 */
    private fun initView() {
        /*  아이템 text 초기 설정*/
    }

    private fun initViewModel() = with(viewModel) {
        bodyStatus.observe(viewLifecycleOwner) { bodyStatus ->
            // 라이브 데이터 변경 감지
            binding.statusWeightView.text = getString(R.string.weight_value, bodyStatus.weight)
            binding.statusHeightView.text = getString(R.string.height_value, bodyStatus.height)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() = with(binding) {
        // 사용자 건강 상태 입력 다이얼로그 표시
        fixed.setOnClickListener {
            showDialog(ExerciseStatusFragmentDirections.actionExerciseStatusFragmentToExerciseStatusDailyEditDialog())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /* main_graph의 action을 활용해서 dialog 띄우기 */
    private fun showDialog(action: NavDirections) {
        findNavController().navigate(action)
    }

}