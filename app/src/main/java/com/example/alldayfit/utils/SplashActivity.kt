package com.example.alldayfit.utils

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.alldayfit.databinding.SplashActivityBinding
import com.example.alldayfit.settings.login.GoogleSignInPage

class SplashActivity : AppCompatActivity() {
    private lateinit var binding : SplashActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.splashImage.playAnimation()

        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this, GoogleSignInPage::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}