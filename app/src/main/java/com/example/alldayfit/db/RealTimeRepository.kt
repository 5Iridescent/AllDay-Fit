package com.example.alldayfit.db

import com.example.alldayfit.community.model.CommunityPostEntity
import com.example.alldayfit.db.model.FirebaseModel
import com.example.alldayfit.main.model.DailyExercise
import com.example.alldayfit.utils.Util
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

interface RealTimeRepository {

    fun getUserReference(path: String, id: String = userId): DatabaseReference {
        return database.reference.child(path).child(id)
    }

    fun getReference(path: String): DatabaseReference {
        return database.reference.child(path)
    }

    private val userId: String
        get() = "NTqKsmydkVOE9fWxxTolGDSkL7p2"
    private val database: FirebaseDatabase
        get() = Firebase.database(Util.realtime_database)

    fun getUserData()

    fun addExercise(data: FirebaseModel.ExerciseRecord)
    fun fetchWeekData(): MutableList<DailyExercise>

    fun addMealAll(data: FirebaseModel.DietRecord)

    fun addMealOne(mealType: String, data: FirebaseModel.DietRecord)

    fun isPresenceDataExercise(date: String)

    fun addPost(content: CommunityPostEntity): String?
    fun updatePost(content: CommunityPostEntity)
    fun removePost(content: CommunityPostEntity)
    fun getPosts(id: String = userId): MutableList<FirebaseModel.Post>
    fun changeModel(content: CommunityPostEntity): FirebaseModel.Post {
        return FirebaseModel.Post(
            userId,
            content.title,
            content.postingDate,
            content.nickname,
            content.content
        )
    }

    companion object {
        const val USERS = "users"
        const val INFORMATION = "information"
        const val PHYSICAL = "physicalLog"
        const val EXERCISE = "exerciseLog"
        const val DIET = "dietLog"
        const val POST = "post"
        const val BREAKFAST = "Breakfast"
        const val LUNCH = "Lunch"
        const val DINNER = "Dinner"
        const val SNACK = "Snack"
        const val DATE = "LogDate"
        const val POSTDATE = "postingDate"
    }

}