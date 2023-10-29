package com.example.alldayfit.dietrecord

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alldayfit.databinding.DietRecordAddDialogBinding
import com.example.alldayfit.dietrecord.adapter.DietRecordAdapter

class DietRecordAddDialog : DialogFragment() {
    private var _binding: DietRecordAddDialogBinding? = null
    private val binding get() = _binding!!

    // adapter data list
    private val dietRecordsList: MutableList<String> = mutableListOf()

    // listview adapter
    private val dietRecordAdapter by lazy { DietRecordAdapter(dietRecordsList) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DietRecordAddDialogBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() = with(binding) {
        // RecyclerView 및 어댑터 초기화
        mealListview.layoutManager = LinearLayoutManager(requireContext())
        mealListview.adapter = dietRecordAdapter
        // edittext 옆 식사 추가 button을 눌렀을 때 클릭 리스너
        btnAdd.setOnClickListener {
            addMealText()
        }
        // close button을 눌렀을 때 창이 닫히게 해주는 클릭 리스너
        closeBtn.setOnClickListener {
            dismiss()
        }
        finishBtn.setOnClickListener {
            dismiss()
        }
    }

    private fun addMealText() {        // 식단 입력 후 plus 버튼 눌렀을 때 음식이 추가되는 클릭 리스너
        val dietRecord = binding.mealEdit.text.toString()
        //editText가 빈칸일 때 toast 메세지 띄우고 식단 추가 되지 않음
        if (dietRecord.isNotEmpty()) {
            dietRecordsList.add(dietRecord)
            Log.d("yjRyu", "dietRecordsList = $dietRecordsList")
            dietRecordAdapter.notifyDataSetChanged()
            Toast.makeText(requireContext(), "식단 추가 완료!", Toast.LENGTH_SHORT).show()
            //식단 기록 입력 후 editText 초기화
            binding.mealEdit.text.clear()
        } else {
            Toast.makeText(requireContext(), "식단을 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}