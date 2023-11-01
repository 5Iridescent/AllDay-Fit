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

interface RealTimeRepository {

    fun getReference(path: String, id: String = userId): DatabaseReference {
        return database.reference.child(path).child(id)
    }

    private val userId: String
        get() = "NTqKsmydkVOE9fWxxTolGDSkL7p2"
    private val database: FirebaseDatabase
        get() = FirebaseDatabase.getInstance(Util.realtime_database)

    /* 현 유저의 고유 user id를 가지고 user 테이블에 접근하여 데이터 가져오기 */
    fun getUserData() {}

    fun addExercise(data: FirebaseModel.ExerciseRecord) {}

    fun addMealAll(data: FirebaseModel.DietRecord) {}

    fun addMealOne(mealType: String, data: FirebaseModel.DietRecord) {}

    fun isPresenceDataExercise(date: String) {}

    fun retrievePosts() {}

    companion object {
        const val USERS = "users"
        const val INFORMATION = "information"
        const val PHYSICAL = "physicalLog"
        const val EXERCISE = "exerciseLog"
        const val DIET = "dietLog"
        const val POST = "postLog"
        const val BREAKFAST = "Breakfast"
        const val LUNCH = "Lunch"
        const val DINNER = "Dinner"
        const val SNACK = "Snack"
        const val DATE = "LogDate"
    }
}