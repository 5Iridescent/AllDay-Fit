package com.example.alldayfit.db

import android.util.Log
import com.example.alldayfit.community.model.CommunityPostEntity
import com.example.alldayfit.db.model.FirebaseModel
import com.example.alldayfit.main.model.DailyExercise
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class RealTimeRepositoryImpl() : RealTimeRepository {

    private val userRef = getUserReference(RealTimeRepository.USERS)
    private val mealRef = getUserReference(RealTimeRepository.DIET)

    private val exerciseRef = getUserReference(RealTimeRepository.EXERCISE)
    private val postRef = getReference(RealTimeRepository.POST)

    override fun getUserData() {
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val userData = dataSnapshot.getValue(FirebaseModel.UserData::class.java)
                } else {
                    Log.d("firebase", "NO DATA")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("firebase", "ERROR GET USER DATA")
            }
        })
    }

    override fun addExercise(data: FirebaseModel.ExerciseRecord) {
        val query = exerciseRef.orderByChild(RealTimeRepository.DATE).equalTo(data.logDate)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (snapshot in dataSnapshot.children) {
                        val id = snapshot.key
                        if (id != null) {
                            val existingData =
                                snapshot.getValue(FirebaseModel.ExerciseRecord::class.java)
                            Log.d("test", snapshot.value.toString())
                            if (existingData != null) {
                                exerciseRef.child(id).setValue(
                                    FirebaseModel.ExerciseRecord(
                                        existingData.totalTime + data.totalTime,
                                        data.logDate
                                    )
                                )
                                return
                            }
                        }
                    }
                } else {
                    exerciseRef.push().setValue(data)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("FirebaseError", "Error: ${databaseError.message}")
            }
        })
    }

    override fun fetchWeekData(): MutableList<DailyExercise> {
        val query = exerciseRef.orderByChild(RealTimeRepository.DATE).limitToLast(7)
        val dataList = mutableListOf<DailyExercise>()
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val dbData = dataSnapshot.getValue(FirebaseModel.ExerciseRecord::class.java)
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    dbData?.let {
                        dataList.add(
                            DailyExercise(
                                it.totalTime,
                                LocalDate.parse(it.logDate, formatter)
                            )
                        )
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Error fetching data: $error")
            }
        })
        return dataList
    }

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
            }
        })
    }

    override fun isPresenceDataExercise(date: String) {
        val query = exerciseRef.orderByChild(RealTimeRepository.USERS).equalTo(date)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                } else {
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

    override fun addPost(content: CommunityPostEntity): String? {
        val postId = postRef.push().key
        if (postId != null) {
            postRef.child(postId).setValue(changeModel(content))
        }
        return postId
    }

    override fun updatePost(content: CommunityPostEntity) {
        val postId = content.postId
        postRef.child(postId).setValue(changeModel(content))
    }

    override fun removePost(content: CommunityPostEntity) {
        val postId = content.postId
        postRef.child(postId).removeValue()
    }

    private var startAtKey: String? = null
    override fun getPosts(id: String): MutableList<FirebaseModel.Post> {
        val query = postRef.limitToLast(20)
            .orderByChild(RealTimeRepository.POSTDATE)
        if (startAtKey != null) {
            query.endAt(startAtKey)
        }
        val dataList = mutableListOf<FirebaseModel.Post>()
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (data in dataSnapshot.children) {
                        val item = data.getValue(FirebaseModel.Post::class.java)
                        item?.let { dataList.add(it) }
                    }
                    startAtKey = dataSnapshot.children.reversed().lastOrNull()?.key.toString()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
        return dataList
    }

    companion object {
        private var instace: RealTimeRepositoryImpl? = null
        fun getInstance(): RealTimeRepositoryImpl = instace ?: synchronized(this) {
            instace ?: RealTimeRepositoryImpl().also { instace = it }
        }
    }
}