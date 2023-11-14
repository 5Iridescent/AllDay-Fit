package com.example.alldayfit.settings.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.alldayfit.MainActivity
import com.example.alldayfit.databinding.EmailSignUpActivityBinding
import com.google.firebase.auth.FirebaseAuth


class EmailSignUpActivity : AppCompatActivity() {

    private lateinit var binding: EmailSignUpActivityBinding
    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EmailSignUpActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()

        val editTextEmail: EditText = binding.editTextEmail
        val editTextPassword: EditText = binding.editTextPassword
        val buttonSignUp: Button = binding.buttonSignUp

        buttonSignUp.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            mAuth.createUserWithEmailAndPassword(email, password) // 회원 가입
                .addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                        Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                        if (mAuth.currentUser != null) {
                            val intent = Intent(this, GoogleSignInPage::class.java)
                            startActivity(intent)
                        }
                    } else if (result.exception?.message.isNullOrEmpty()) {
                        Toast.makeText(this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        login(email, password)
                    }
                }
        }
    }

    private fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password) // 로그인
            .addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
    }
}