package com.example.alldayfit.utils

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.example.alldayfit.MainActivity
import com.example.alldayfit.R
import com.example.alldayfit.settings.GoogleSignInPage

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)
        var lodingImage = findViewById(R.id.splash_image) as LottieAnimationView

        // 애니메이션 시작
        lodingImage.playAnimation()

        val handler: Handler = Handler()
        handler.postDelayed({
            val intent = Intent(this, GoogleSignInPage::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}