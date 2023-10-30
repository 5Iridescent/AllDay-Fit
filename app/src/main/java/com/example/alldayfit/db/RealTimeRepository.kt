package com.example.alldayfit.db

import android.content.Context
import android.util.Log
import com.example.alldayfit.db.model.FirebaseModel
import com.example.alldayfit.utils.Util
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RealTimeRepository() {

    private fun getReference(path: String): DatabaseReference {
        return database.reference.child(path).child(userId)
    }

    private val database =
        FirebaseDatabase.getInstance(Util.realtime_database)
    private val userId = "2vnd98tu34-jfdkjdsahg"

    //    private val listener = object : ValueEventListener
    private val userRef = getReference("users")
    private val mealRef = getReference("dietLog")
    private val informationRef = getReference("physicalInformation")
    private val exerciseRef = getReference("exerciseLog")

    /* 현 유저의 고유 user id를 가지고 user 테이블에 접근하여 데이터 가져오기 */
    fun getUserData() {
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d("test", "start")
                if (dataSnapshot.exists()) {
                    val userData = dataSnapshot.getValue(FirebaseModel.UserData::class.java)
                    // 사용자 정보를 이용하여 무엇인가를 수행합니다.z`
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
    //
    private fun addMealAll(date: String, mealData: FirebaseModel.DietRecord) {
        val query = mealRef.orderByChild("mealDate").equalTo(date)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val id = dataSnapshot.key
                    if (id != null) {
                        mealRef.child(id).setValue(mealData)
                    }
                } else {
                    mealRef.push().setValue(mealData)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 에러
            }
        })
    }

    private fun addMealOne(mealType: String, mealData: FirebaseModel.DietRecord) {
        val query = mealRef.orderByChild("mealDate").equalTo(mealData.mealDate)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val id = dataSnapshot.key
                    if (id != null) {
                        mealRef.child(id).child(mealType).setValue(mealData)
                    }
                } else {
                    val mealId = mealRef.push().key
                    if (mealId != null) {
                        mealRef.child(mealId).child(mealType).setValue(mealData)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 에러
            }
        })
    }

    private fun isPresenceDataExercise(date: String) {
        val query = exerciseRef.orderByChild("exerciseDate").equalTo(date)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    // 이미 데이터가 있음
                } else {
                    // 데이터가 없음
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 에러
            }
        })
    }

    companion object {
        private var instace: RealTimeRepository? = null
        fun getInstance(): RealTimeRepository = instace ?: synchronized(this) {
            instace ?: RealTimeRepository().also { instace = it }
        }
        private const val users ="users"
        private const val diet ="dietLog"
        private const val breakfast ="breakfast"
        private const val lunch ="lunch"
        private const val dinner ="dinner"
        private const val snack ="snack"
        private const val exercise ="exerciseLog"
    }
}