package com.example.alldayfit.dietrecord

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alldayfit.R
import com.example.alldayfit.databinding.DietRecordAddDialogBinding
import com.example.alldayfit.databinding.DietRecordFragmentBinding

class DietRecordFragment : Fragment() {
    private var _binding: DietRecordFragmentBinding? = null
    private val binding get() = _binding!!
    private val dietRecordsList: MutableList<String> = mutableListOf()
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var dietRecordAdapter: DietRecordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DietRecordFragmentBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() = with(binding) {
        breakfastView.mealTxt.text = getString(R.string.diet_record_breakfast)
        lunchView.mealTxt.text = getString(R.string.diet_record_lunch)
        dinnerView.mealTxt.text = getString(R.string.diet_record_dinner)
        snackView.mealTxt.text = getString(R.string.diet_record_snack)
        carbohydratesView.analysisTxt.text = getString(R.string.carbohydrates)
        proteinView.analysisTxt.text = getString(R.string.protein)
        fatView.analysisTxt.text = getString(R.string.fat)
        caloriesView.analysisTxt.text = getString(R.string.calories)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        recyclerView = view.findViewById(R.id.meal_listview)
//        dietRecordAdapter = DietRecordAdapter(dietRecordsList)
//
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        recyclerView.adapter = dietRecordAdapter

        val addMealView = view.findViewById<ImageView>(R.id.add_meal_view)
        addMealView.setOnClickListener {
            showDietRecordAddDialog()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = DietRecordFragment()
    }

    // 식단 기록의 ImageButton을 클릭했을 때 식단 기록 추가 다이얼로그를 띄우는 함수
    private fun showDietRecordAddDialog() {
        val dialogBinding = DietRecordAddDialogBinding.inflate(layoutInflater)
        val dialogView = dialogBinding.root

        val builder = AlertDialog.Builder(requireContext())
            .setView(dialogView)

        val alertDialog = builder.create()

        // RecyclerView 및 어댑터 초기화
        val recyclerView = dialogBinding.mealListview
        val dietRecordAdapter = DietRecordAdapter(dietRecordsList)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = dietRecordAdapter

        // 식단 입력 후 plus 버튼 눌렀을 때 음식이 추가되는 클릭 리스너
        dialogBinding.btnAdd.setOnClickListener {
            val dietRecord = dialogBinding.mealEdit.text.toString()
            //editText가 빈칸일 때 toast 메세지 띄우고 식단 추가 되지 않음
            if (dietRecord.isNotEmpty()) {
                dietRecordsList.add(dietRecord)
                Log.d("yjRyu", "dietRecordsList = $dietRecordsList")
                dietRecordAdapter.notifyDataSetChanged()
                Toast.makeText(requireContext(), "식단 추가 완료!", Toast.LENGTH_SHORT).show()
                //식단 기록 입력 후 editText 초기화
                dialogBinding.mealEdit.text.clear()
            } else {
                Toast.makeText(requireContext(), "식단을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        // close button을 눌렀을 때 창이 닫히게 해주는 클릭 리스너
        dialogBinding.closeBtn.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()
    }
}