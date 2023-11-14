package com.example.alldayfit.dietrecord

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.alldayfit.databinding.DietRecordAddDialogBinding
import com.example.alldayfit.dietrecord.adapter.DietRecordAdapter
import java.io.File

class DietRecordAddDialog : DialogFragment() {
    private val sharedViewModel: SharedViewModel by lazy {
        ViewModelProvider(requireActivity())[SharedViewModel::class.java]
    }

    private var _binding: DietRecordAddDialogBinding? = null
    private val binding get() = _binding!!

    private val args: DietRecordAddDialogArgs by navArgs()
    private val mealType: String by lazy { args.mealType }
    private val PICK_IMAGE_REQUEST = 1

    private val dietRecordsList: MutableList<String> = mutableListOf()

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
        mealText.text = mealType
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() = with(binding) {
        mealListview.layoutManager = LinearLayoutManager(requireContext())
        mealListview.adapter = dietRecordAdapter
        btnAdd.setOnClickListener {
            addMealText()
        }
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    (ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(
                            Manifest.permission.READ_MEDIA_IMAGES,
                        ),
                        1
                    )
                            )
                }
            } else {
                openGallery()
            }
        }
    }

    @SuppressLint("IntentReset")
        private fun openGallery() {
            try {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                intent.type = "image/*"
                startActivityForResult(intent, PICK_IMAGE_REQUEST)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "갤러리를 열 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }

    private fun getRealPathFromURI(uri: Uri): String {
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


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val imageUri: Uri = data.data!!
            val newImageUri = getRealPathFromURI(imageUri)

            sharedViewModel.setSelectedImageUri(Uri.fromFile(File(newImageUri)))

            Glide.with(requireContext())
                .load(File(newImageUri).path)
                .centerCrop()
                .into(binding.dietImg)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun addMealText() {
        val dietRecord = binding.mealEdit.text.toString()
        if (dietRecord.isNotEmpty()) {
            dietRecordsList.add(dietRecord)
            dietRecordAdapter.notifyDataSetChanged()
            Toast.makeText(requireContext(), "식단 추가 완료!", Toast.LENGTH_SHORT).show()
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