package com.example.alldayfit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.alldayfit.community.CommunityHomeFragment
import com.example.alldayfit.community.CommunityMainFragment
import com.example.alldayfit.databinding.ExerciseStatusFragmentBinding
import com.example.alldayfit.databinding.MainActivityBinding
import com.example.alldayfit.db.RealTimeRepository
import com.example.alldayfit.dietrecord.DietRecordFragment
import com.example.alldayfit.exercisestatus.ExerciseStatusFragment
import com.example.alldayfit.main.MainFragment
import com.example.alldayfit.settings.ui.SettingMainFragment
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = MainActivityBinding.inflate(layoutInflater)
        val view = mBinding.root
        initNavigationBar()
        setCustomToolbar()
        setContentView(view)
    }

    /* bottom navigation click event - 클릭 */
    private fun initNavigationBar() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(mBinding.mainFrame.id) as NavHostFragment
        val navController = navHostFragment.navController // 기본 선택 아이템
        var lastSelectedItemId = R.id.mainFragment
        mBinding.mainNav.setOnItemSelectedListener { item ->
            navController.navigate(item.itemId) // 아이템 선택
            lastSelectedItemId = item.itemId
            true
        }
        mBinding.mainNav.selectedItemId = R.id.mainFragment
    }

    // 툴바 메뉴 버튼을 설정- menu에 있는 item을 연결하는 부분
    fun setCustomToolbar() {
        val toolbar = mBinding.mainToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val mTitle = mBinding.toolbarTitle
        mTitle.text = "All_DayFit"
    }

    // 툴바 메뉴 버튼을 설정- menu에 있는 item을 연결하는 부분
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_toolbar_menu, menu)
        return true
    }

}