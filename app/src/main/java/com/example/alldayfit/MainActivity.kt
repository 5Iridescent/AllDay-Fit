package com.example.alldayfit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.fragment.app.Fragment
import com.example.alldayfit.community.CommunityHomeFragment
import com.example.alldayfit.databinding.ExerciseStatusFragmentBinding
import com.example.alldayfit.databinding.MainActivityBinding
import com.example.alldayfit.dietrecord.DietRecordFragment
import com.example.alldayfit.exercisestatus.ExerciseStatusFragment
import com.example.alldayfit.main.MainFragment
import com.example.alldayfit.settings.ui.SettingMainFragment

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: MainActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = MainActivityBinding.inflate(layoutInflater)
        val view = mBinding.root
        initNavigationBar()
        setContentView(view)
        setCustomToolbar()
    }

    /* bottom navigation click event - 클릭 */
    private fun initNavigationBar() {
        mBinding.mainNav.run {
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.app_bar_com -> {
                        replaceFragment(CommunityHomeFragment.newInstance())
                    }

                    R.id.app_bar_diet -> {
                        replaceFragment(DietRecordFragment.newInstance())
                    }

                    R.id.app_bar_main -> {
                        replaceFragment(MainFragment.newInstance())
                    }

                    R.id.app_bar_mypage -> {
                        replaceFragment(ExerciseStatusFragment.newInstance())
                    }

                    R.id.app_bar_menu -> {
                        replaceFragment(SettingMainFragment.newInstance())
                    }
                }
                true
            }
            selectedItemId = R.id.app_bar_main
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val nFragment = supportFragmentManager.findFragmentById(mBinding.mainFrame.id)
//        if (fragment) {
//            Log.d("test","null")
//        }
        supportFragmentManager.beginTransaction().apply {
            replace(mBinding.mainFrame.id, fragment)
            commit()
        }
    }

    // 툴바 메뉴 버튼을 설정- menu에 있는 item을 연결하는 부분
    fun setCustomToolbar() {
        val toolbar = mBinding.mainToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val mTitle = mBinding.toolbarTitle
        mTitle.text = "All_DayFit"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_toolbar_menu, menu)
        return true
    }

}