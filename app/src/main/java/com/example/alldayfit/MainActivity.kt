package com.example.alldayfit

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.alldayfit.databinding.MainActivityBinding
import com.kakao.sdk.common.util.Utility

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding
    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(binding.mainFrame.id) as NavHostFragment
    }
    private val navController by lazy { navHostFragment.navController }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        initView()
        setContentView(binding.root)
        val keyHash = Utility.getKeyHash(this)
        Log.d("Key555", keyHash)
    }

    private fun initView() = with(binding) {
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