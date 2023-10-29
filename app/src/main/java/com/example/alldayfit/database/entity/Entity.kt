package com.example.alldayfit.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val email: String,
    val name: String,
    val nickname: String
)

@Entity(tableName = "physical_information")
data class PhysicalInformationEntity(
    @PrimaryKey
    val name: String,
    val inputDate: String, // yyyy.MM.dd 형식의 날짜 문자열
    val height: Int,
    val weight: Int
)

@Entity(tableName = "exercise_logs")
data class ExerciseLogEntity(
    @PrimaryKey
    val name: String,
    val totalTime: Int,
    val exerciseDate: String // yyyy.MM.dd 형식의 날짜 문자열
)

@Entity(tableName = "diet_logs")
data class DietLogEntity(
    @PrimaryKey
    val name: String,
    val breakfastFoodText: List<String>,
    val breakfastFoodImage: String,
    val lunchFoodText: List<String>,
    val lunchFoodImage: String,
    val dinnerFoodText: List<String>,
    val dinnerFoodImage: String,
    val snackFoodText: List<String>,
    val snackFoodImage: String,
    val mealDate: String // yyyy.MM.dd 형식의 날짜 문자열
)
