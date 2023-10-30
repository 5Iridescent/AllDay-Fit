package com.example.alldayfit.dietrecord

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.alldayfit.R
import com.example.alldayfit.databinding.DietRecordAddDialogBinding
import com.example.alldayfit.databinding.DietRecordFragmentBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.io.File

class DietRecordFragment : Fragment() {
    private var _binding: DietRecordFragmentBinding? = null
    private val PICK_IMAGE_REQUEST = 1
    private var dialogBinding: DietRecordAddDialogBinding? = null
    private val binding get() = _binding!!
    private val dietRecordsList: MutableList<String> = mutableListOf()
    private lateinit var dietRecordChart: BarChart
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
        dialogBinding = DietRecordAddDialogBinding.inflate(inflater, container, false)
        initView()

        dietRecordChart = binding.dietRecordChart

        val carbDataSet = BarDataSet(getBarEntries("탄수화물"), "탄수화물")
        val proteinDataSet = BarDataSet(getBarEntries("단백질"), "단백질")
        val fatDataSet = BarDataSet(getBarEntries("지방"), "지방")
        val caloriesDataSet = BarDataSet(getBarEntries("칼로리"), "칼로리")

        carbDataSet.color = ContextCompat.getColor(requireContext(), R.color.green)
        proteinDataSet.color = ContextCompat.getColor(requireContext(), R.color.yellow)
        fatDataSet.color = ContextCompat.getColor(requireContext(), R.color.black)
        caloriesDataSet.color = ContextCompat.getColor(requireContext(), R.color.dark_blue)

        val data = BarData(carbDataSet, proteinDataSet, fatDataSet, caloriesDataSet)
        dietRecordChart.data = data

        data.barWidth = 0.15f

        dietRecordChart.description.isEnabled = false
        dietRecordChart.setDrawGridBackground(false)

        val xAxis = dietRecordChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(arrayOf("탄수화물", "단백질", "지방", "칼로리"))
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        dietRecordChart.groupBars(0f, 0.05f, 0.05f)

        dietRecordChart.invalidate()

        return binding.root
    }

    private fun getBarEntries(s: String): List<BarEntry> {
        val entries = ArrayList<BarEntry>()
        val random = java.util.Random()

        // 하루 식단 기록에 대한 더미 데이터 생성
        val carbs = random.nextFloat() * 100 // 탄수화물
        val protein = random.nextFloat() * 100 // 단백질
        val fat = random.nextFloat() * 100 // 지방
        val calories = (carbs * 4 + protein * 4 + fat * 9).toFloat() // 칼로리 계산

       entries.add(BarEntry(0f, carbs))
        entries.add(BarEntry(1f, protein))
        entries.add(BarEntry(2f, fat))
        entries.add(BarEntry(3f, calories))

        return entries
    }

    private fun initView() = with(binding) {
        breakfastView.mealTxt.text = getString(R.string.diet_record_breakfast)
        lunchView.mealTxt.text = getString(R.string.diet_record_lunch)
        dinnerView.mealTxt.text = getString(R.string.diet_record_dinner)
        snackView.mealTxt.text = getString(R.string.diet_record_snack)
//        carbohydratesView.analysisTxt.text = getString(R.string.carbohydrates)
//        proteinView.analysisTxt.text = getString(R.string.protein)
//        fatView.analysisTxt.text = getString(R.string.fat)
//        caloriesView.analysisTxt.text = getString(R.string.calories)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addMealView = view.findViewById<ImageView>(R.id.add_meal_view)
        addMealView.setOnClickListener {
            showDietRecordAddDialog()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        dialogBinding = null
    }

    companion object {
        fun newInstance() = DietRecordFragment()
    }

    // 식단 기록의 ImageButton을 클릭했을 때 식단 기록 추가 다이얼로그를 띄우는 함수
    private fun showDietRecordAddDialog() {
      dialogBinding?.let { dialogBinding ->

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
                  dietRecordAdapter.notifyDataSetChanged()
                  Toast.makeText(requireContext(), "식단 추가 완료!", Toast.LENGTH_SHORT).show()
                  //식단 기록 입력 후 editText 초기화
                  dialogBinding.mealEdit.text.clear()
              } else {
                  Toast.makeText(requireContext(), "식단을 입력해주세요.", Toast.LENGTH_SHORT).show()
              }
          }

          dialogBinding.closeBtn.setOnClickListener {
              alertDialog.dismiss()
              (dialogBinding.root.parent as ViewGroup).removeView(dialogBinding.root)
          }

          dialogBinding.finishBtn.setOnClickListener {
              if(dietRecordsList.isEmpty()){
                  Toast.makeText(requireContext(), "식단을 입력해주세요.", Toast.LENGTH_SHORT).show()
              } else {
                  alertDialog.dismiss()
                  (dialogBinding.root.parent as ViewGroup).removeView(dialogBinding.root)
              }
          }

          //권한체크 후 갤러리 열어주는 코드
          dialogBinding.dietImg.setOnClickListener{
              if (ActivityCompat.checkSelfPermission(
                      requireContext(),
                      Manifest.permission.READ_MEDIA_IMAGES,
                  ) != PackageManager.PERMISSION_GRANTED
              ) {
                  (ActivityCompat.requestPermissions(
                      requireActivity(),
                      arrayOf(
                          Manifest.permission.READ_MEDIA_IMAGES,
                      ),
                      1
                  )
                          )
              } else {
                  openGallery()
              }
          }
          alertDialog.show()

      }
    }
    private fun openGallery() {
        try {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        } catch (e: Exception) {
            // 갤러리 열기 중에 문제 발생 시 처리
            e.printStackTrace()
            Toast.makeText(requireContext(), "갤러리를 열 수 없습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    // 상대경로를 절대경로로 바꿔주는 코드
    fun getRealPathFromURI(uri: Uri): String {
        val buildName = Build.MANUFACTURER
        if (buildName.equals("Xiami")) {
            return uri.path!!
        }
        var columnIndex = 0
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor =
            (activity)?.contentResolver?.query(
                uri,
                proj,
                null,
                null,
                null
            )
        if (cursor!!.moveToFirst()) {
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }
        val result = cursor.getString(columnIndex)
        cursor.close()
        return result
    }

    // 이미지 선택 결과 처리
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            val imageUri: Uri = data.data!!
            val newImageUri = getRealPathFromURI(imageUri)

            // Glide
            Glide.with(requireContext())
                .load(File(newImageUri).path)
                .centerCrop()
                .into(dialogBinding!!.dietImg)
    }
}
}