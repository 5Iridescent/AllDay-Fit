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
        val navHostFragment =
            supportFragmentManager.findFragmentById(mainFrame.id) as NavHostFragment
        val navController = navHostFragment.navController
        mainNav.setOnItemSelectedListener { item ->
            navController.navigate(item.itemId)
            true
        }
        mainNav.selectedItemId = R.id.mainFragment
        setSupportActionBar(mainToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.title = getString(R.string.app_title)
        supportActionBar?.title = title
        toolbarTitle.text = getString(R.string.app_title)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_toolbar_menu, menu)
        return true
    }

}