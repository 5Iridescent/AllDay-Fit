package com.example.alldayfit.settings.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.alldayfit.MainActivity
import com.example.alldayfit.R
import com.example.alldayfit.databinding.SignInPageActivityBinding
import com.example.alldayfit.utils.Util
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class GoogleSignInPage : AppCompatActivity() {
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: SignInPageActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignInPageActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(Util.google_login) // 웹 클라이언트 ID로부터 ID 토큰 요청
            .requestEmail() // 사용자 이메일 정보 요청
            .build()

        // GoogleSignInClient 초기화
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // FirebaseAuth 인스턴스 초기화
        firebaseAuth = FirebaseAuth.getInstance()

        // Google 로그인 버튼 클릭 이벤트 처리
        binding.googleSignInButton.setOnClickListener {
            signInWithGoogle() // Google 로그인 함수 호출
        }

        // Email 회원가입 버튼 클릭 이벤트 처리
        binding.emailSignUp.setOnClickListener {
            signUpEmailPage()
        }
        // 기존 사용자 로그인 버튼 클릭 이벤트 처리
        binding.buttonLogin.setOnClickListener {
            signIn(binding.editTextEmail.text.toString(), binding.editTextPassword.text.toString())
        }
    }

    private fun signIn(email: String, password: String) {

        if (email.isNotEmpty() && password.isNotEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            baseContext, "로그인에 성공 하였습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                        moveMainPage(firebaseAuth.currentUser)
                    } else {
                        Toast.makeText(
                            baseContext, "로그인에 실패 하였습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    // 유저정보 넘겨주고 메인 액티비티 호출
    private fun moveMainPage(user: FirebaseUser?) {
        if (user != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }


    // Email 회원가입 페이지
    private fun signUpEmailPage() {
        startActivity(Intent(this, EmailSignUpActivity::class.java))

    }

    // Google 로그인 시작
    private fun signInWithGoogle() {
        Log.d("GoogleSignIn", "signInWithGoogle() called")
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN) // 로그인 Intent 시작
    }

    // startActivityForResult로부터 결과 처리
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            Log.d(
                "GoogleSignIn",
                "onActivityResult: requestCode=$requestCode, resultCode=$resultCode"
            )
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    firebaseAuthWithGoogle(account) // Google 로그인 정보로 Firebase 인증 시작
                }
            } catch (e: ApiException) {
                Log.e("GoogleSignIn", "Google sign in failed: ${e.message}")
                // Google 로그인 실패 처리
                Toast.makeText(this, "Google 로그인 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Firebase로부터 Google 로그인 정보를 사용하여 인증 처리
    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        Log.d("GoogleSignIn", "firebaseAuthWithGoogle() called")
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Firebase로부터 로그인 성공 처리
                    val user = firebaseAuth.currentUser
                    Log.d("GoogleSignIn", "Firebase 로그인 성공: ${user?.displayName}")
                    Toast.makeText(this, "Google 로그인 성공: ${user?.displayName}", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    // Firebase로부터 로그인 실패 처리
                    Log.e("GoogleSignIn", "Firebase 로그인 실패: ${task.exception}")
                    Toast.makeText(this, "Google 로그인 실패", Toast.LENGTH_SHORT).show()
                }
            }
    }

    companion object {
        private const val RC_SIGN_IN = 9001 // Google 로그인 요청 코드
    }
}
