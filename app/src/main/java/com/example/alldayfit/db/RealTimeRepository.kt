package com.example.alldayfit.db

import android.util.Log
import com.example.alldayfit.db.model.FirebaseModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RealTimeRepository {

    private val database = FirebaseDatabase.getInstance()
    private val userId = "2vnd98tu34-jfdkjdsahg"

    //    private val listener = object : ValueEventListener
    private val userRef = database.getReference("users/$userId")
    private val mealRef = database.getReference("dietLog/$userId")
    fun init(){

    }

    /* 현 유저의 고유 user id를 가지고 user 테이블에 접근하여 데이터 가져오기 */
    fun getUserData() {
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val name = dataSnapshot.child("name").getValue(String::class.java)
                    val email = dataSnapshot.child("email").getValue(String::class.java)
                    // 사용자 정보를 이용하여 무엇인가를 수행합니다.
                    Log.d("test","$name,$email")
                } else {
                    // 데이터가 존재하지 않을 때의 처리
                    Log.d("firebase", "NO DATA")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 에러 처리
                Log.d("firebase", "ERROR GET USER DATA")
            }
        })
    }

    fun addExercise() {

    }

    private fun isPresenceMealData(){

    }
    fun updateMeal(){

    }
    fun addMealAll(mealAll : FirebaseModel.DietRecord) {
        mealAll.mealDate
        mealRef.push().setValue(mealAll)
    }
    fun addMealBreakfast() {
    }
    fun addMealLunch() {
    }
    fun addMealDinner() {
    }
    fun addMealSnack() {
    }

    companion object {
        private var instace: RealTimeRepository? = null
        fun getInstance(): RealTimeRepository = instace ?: synchronized(this) {
            instace ?: RealTimeRepository().also { instace = it }
        }
    }
}