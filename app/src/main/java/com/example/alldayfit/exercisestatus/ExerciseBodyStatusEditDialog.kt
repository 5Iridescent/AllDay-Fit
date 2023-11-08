package com.example.alldayfit.exercisestatus

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.alldayfit.databinding.ExerciseStatusDailyEditDialogBinding

class ExerciseStatusDailyEditDialog : DialogFragment() {
    private var _binding: ExerciseStatusDailyEditDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: BodyStatusViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ExerciseStatusDailyEditDialogBinding.inflate(inflater, container, false)
        initView()
        viewModel = ViewModelProvider(requireActivity())[BodyStatusViewModel::class.java]
        return binding.root
    }

    /* dialog design, data 초기 설정 */
    private fun initView() = with(binding) {
        etHeight.setText(viewModel.bodyStatus.value?.height)
        etWeight.setText(viewModel.bodyStatus.value?.weight)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() = with(binding) {
        etHeight.addTextChangedListener {
            object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(h: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    viewModel.updateBodyHeight(h.toString())
                }

                override fun afterTextChanged(p0: Editable?) {
                }
            }
        }
        etWeight.addTextChangedListener {
            object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(w: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    viewModel.updateBodyWeight(w.toString())
                }

                override fun afterTextChanged(p0: Editable?) {
                }
            }
        }
        closeBtn.setOnClickListener {
            dismiss()
        }
        correctionComplete.setOnClickListener {
//            val height =
//                setFragmentResult(EDIT_PI, bundleOf(EDIT_PI_HEIGHT to))
//            val weight = binding.statusWeightView.statusMetricsInputTxt.text.toString()
//            val height = binding.statusHeightView.statusMetricsInputTxt.text.toString()

//            val statusEdit = BodyStatusEdit(weight, height)
            // ViewModel을 통해 데이터 업데이트
//            viewModel.setBodyStatus(statusEdit)
            dismiss()
        }
    }
}