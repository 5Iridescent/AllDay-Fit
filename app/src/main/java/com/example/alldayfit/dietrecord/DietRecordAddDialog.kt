package com.example.alldayfit.dietrecord

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.alldayfit.databinding.DietRecordAddDialogBinding
import com.example.alldayfit.dietrecord.adapter.DietRecordAdapter
import java.io.File

class DietRecordAddDialog : DialogFragment() {
    private var _binding: DietRecordAddDialogBinding? = null
    private val binding get() = _binding!!
    private val PICK_IMAGE_REQUEST = 1
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
            if (dietRecordsList.isEmpty()) {
                Toast.makeText(requireContext(), "식단을 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                dismiss()
            }
        }
        dietImg.setOnClickListener {
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
            // 식단 입력 후 plus 버튼 눌렀을 때 음식이 추가되는 클릭 리스너
        }
    }

    private fun openGallery() {
        try {
            val intent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
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
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val imageUri: Uri = data.data!!
            val newImageUri = getRealPathFromURI(imageUri)

            // Glide
            Glide.with(requireContext())
                .load(File(newImageUri).path)
                .centerCrop()
                .into(binding.dietImg)

        }
    }

    private fun addMealText() {        // 식단 입력 후 plus 버튼 눌렀을 때 음식이 추가되는 클릭 리스너
        val dietRecord = binding.mealEdit.text.toString()
        //editText가 빈칸일 때 toast 메세지 띄우고 식단 추가 되지 않음
        if (dietRecord.isNotEmpty()) {
            dietRecordsList.add(dietRecord)
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