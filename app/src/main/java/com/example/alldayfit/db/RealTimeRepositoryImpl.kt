package com.example.alldayfit.db

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.alldayfit.community.CommunityViewModel
import com.example.alldayfit.community.model.CommunityPostEntity
import com.example.alldayfit.db.model.FirebaseModel
import com.example.alldayfit.main.model.DailyExercise
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class RealTimeRepositoryImpl() : RealTimeRepository {

    //    private val listener = object : ValueEventListener
    private val userRef = getUserReference(RealTimeRepository.USERS)
    private val mealRef = getUserReference(RealTimeRepository.DIET)
    private val physicalRef = getUserReference(RealTimeRepository.PHYSICAL)
    private val userInfoRef = getUserReference(RealTimeRepository.INFORMATION)
    private val exerciseRef = getUserReference(RealTimeRepository.EXERCISE)
    private val postRef = getReference(RealTimeRepository.POST)

    /* 현 유저의 고유 user id를 가지고 user 테이블에 접근하여 데이터 가져오기 */
    override fun getUserData() {
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val userData = dataSnapshot.getValue(FirebaseModel.UserData::class.java)
                    // 사용자 정보를 이용하여 무엇인가를 수행합니다.
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
        postRef.child("/-Nio9Wm0nb_8IytdXCtg").removeValue()
    }

    private var startAtKey: String? = null
    override fun getPosts(id: String): MutableList<FirebaseModel.Post> {
        val query = postRef.limitToLast(20)
            .orderByChild(RealTimeRepository.POSTDATE)  // timestamp는 데이터의 타임스탬프 필드 이름입니다.
        if (startAtKey != null) {
            query.endAt(startAtKey)  // 특정 키부터 시작하는 경우에 사용합니다.
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
                // 에러 처리
            }
        })
        return dataList
    }
    override fun observeList(lists : MutableLiveData<ArrayList<FirebaseModel.Post>>){
        postRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<FirebaseModel.Post>()
                for (snap in snapshot.children){
                    val data = snap.getValue<FirebaseModel.Post>()
                    data?.let {
                        list.add(it)
                    }
                }
                lists.postValue(list)
            }
            override fun onCancelled(error: DatabaseError) {
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