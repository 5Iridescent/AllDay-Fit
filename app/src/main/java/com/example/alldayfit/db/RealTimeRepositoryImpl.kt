package com.example.alldayfit.db

import android.util.Log
import com.example.alldayfit.db.model.FirebaseModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class RealTimeRepositoryImpl() : RealTimeRepository {

    //    private val listener = object : ValueEventListener
    private val userRef = getReference(RealTimeRepository.USERS)
    private val mealRef = getReference(RealTimeRepository.DIET)
    private val informationRef = getReference(RealTimeRepository.PHYSICAL)
    private val exerciseRef = getReference(RealTimeRepository.EXERCISE)

    /* 현 유저의 고유 user id를 가지고 user 테이블에 접근하여 데이터 가져오기 */
    override fun getUserData() {
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
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

    override fun addExercise(data: FirebaseModel.ExerciseRecord) {
        val query = exerciseRef.orderByChild(RealTimeRepository.DATE).equalTo(data.logDate)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val id = dataSnapshot.key
                    if (id != null) {
                        exerciseRef.child(id).setValue(data)
                    }
                } else {
                    exerciseRef.push().setValue(data)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 에러
            }
        })
    }

    //
    override fun addMealAll(data: FirebaseModel.DietRecord) {
        val query = mealRef.orderByChild(RealTimeRepository.DATE).equalTo(data.logDate)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val id = dataSnapshot.key
                    if (id != null) {
                        mealRef.child(id).setValue(data)
                    }
                } else {
                    mealRef.push().setValue(data)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 에러
            }
        })
    }

    override fun addMealOne(mealType: String, data: FirebaseModel.DietRecord) {
        val query = mealRef.orderByChild(RealTimeRepository.DATE).equalTo(data.logDate)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val id = dataSnapshot.key
                    if (id != null) {
                        mealRef.child(id).child(mealType).setValue(data)
                    }
                } else {
                    val mealId = mealRef.push().key
                    if (mealId != null) {
                        mealRef.child(mealId).child(mealType).setValue(data)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 에러
            }
        })
    }

    override fun isPresenceDataExercise(date: String) {
        val query = exerciseRef.orderByChild(RealTimeRepository.USERS).equalTo(date)
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
        private var instace: RealTimeRepositoryImpl? = null
        fun getInstance(): RealTimeRepositoryImpl = instace ?: synchronized(this) {
            instace ?: RealTimeRepositoryImpl().also { instace = it }
        }
    }
}