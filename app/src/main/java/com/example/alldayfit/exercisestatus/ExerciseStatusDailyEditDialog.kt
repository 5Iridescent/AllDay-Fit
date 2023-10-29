package com.example.alldayfit.exercisestatus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.alldayfit.R
import com.example.alldayfit.databinding.ExerciseStatusDailyEditDialogBinding

class ExerciseStatusDailyEditDialog : DialogFragment() {
    private var _binding: ExerciseStatusDailyEditDialogBinding? = null
    private val bindng get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ExerciseStatusDailyEditDialogBinding.inflate(inflater, container, false)
        initView()

        return bindng.root
    }

    /* dialog design, data 초기 설정 */
    private fun initView() = with(bindng) {
        /*  아이템 text 초기 설정*/
        statusWeightView.statusTypeTxt.text = getString(R.string.weight)
        statusHeightView.statusTypeTxt.text = getString(R.string.height)
        statusBmiView.statusTypeTxt.text = getString(R.string.bmi)
        statusExerciseTimeView.statusTypeTxt.text = getString(R.string.exercise_time)
        statusCalorieConsumptionView.statusTypeTxt.text = getString(R.string.calorie_consumption)
        statusCalorieConsumptionView.statusTypeTxt.textSize = 13F

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