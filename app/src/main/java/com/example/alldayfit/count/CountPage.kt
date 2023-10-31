package com.example.alldayfit.count

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.alldayfit.databinding.CountPageActivityBinding


class CountPage : AppCompatActivity() {
    private lateinit var mBinding: CountPageActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = CountPageActivityBinding.inflate(layoutInflater)
        val view = mBinding.root
        setContentView(view)
        mBinding.setRootine.setOnClickListener {
            val countDialog = CountDialog()
            countDialog.show(supportFragmentManager, "CountDialogFragment")
        }
    }



}