package com.example.alldayfit

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.alldayfit.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        initView()
        setContentView(binding.root)
    }

    private fun initView() = with(binding) {
        /* bottom navigation click event - 클릭 */
        val navHostFragment =
            supportFragmentManager.findFragmentById(mainFrame.id) as NavHostFragment
        val navController = navHostFragment.navController // 기본 선택 아이템
        mainNav.setOnItemSelectedListener { item ->
            navController.navigate(item.itemId) // 아이템 선택
            true
        }
        mainNav.selectedItemId = R.id.mainFragment
        // 툴바 메뉴 버튼을 설정- menu에 있는 item을 연결하는 부분
        setSupportActionBar(mainToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.title = getString(R.string.app_title)
        supportActionBar?.title = title
        toolbarTitle.text = getString(R.string.app_title)
    }

    // 툴바 메뉴 버튼을 설정- menu에 있는 item을 연결하는 부분
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_toolbar_menu, menu)
        return true
    }

}