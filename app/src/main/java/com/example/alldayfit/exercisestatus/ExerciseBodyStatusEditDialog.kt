package com.example.alldayfit.exercisestatus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.alldayfit.databinding.ExerciseStatusDailyEditDialogBinding

class ExerciseBodyStatusEditDialog : DialogFragment() {
    private var _binding: ExerciseStatusDailyEditDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: BodyStatusViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ExerciseStatusDailyEditDialogBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[BodyStatusViewModel::class.java]
        initView()
        return binding.root
    }

    /* dialog design, data 초기 설정 */
    private fun initView() = with(binding) {
        etHeight.setText(viewModel.getHeight().toString())
        etWeight.setText(viewModel.getWeight().toString())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() = with(binding) {
        closeBtn.setOnClickListener {
            dismiss()
        }
        correctionComplete.setOnClickListener {
            val height = etHeight.text
            val weight = etWeight.text
            if (height.isNullOrEmpty() || weight.isNullOrEmpty()) {
                return@setOnClickListener
            }
            try {
                // 문자열을 정수로 변환
                viewModel.updateBodyStatus(height.toString().toInt(), weight.toString().toInt())
            } catch (_: NumberFormatException) {
                Toast.makeText(requireContext(), "유효한 값을 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // ViewModel을 통해 데이터 업데이트
            dismiss()
        }
    }

}