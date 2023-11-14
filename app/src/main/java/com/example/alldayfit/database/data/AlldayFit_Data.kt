package com.example.alldayfit.database.data

data class User(
    val email: String,
    val name: String,
    val nickname: String
)

data class InformationId(
    val inputDate: String,
    val height: Int,
    val weight: Int
)

data class PhysicalInformation(
    val name: String,
    val informationId: InformationId
)

data class LogId(
    val totalTime: Int,
    val exerciseDate: String
)

data class ExerciseLog(
    val name: String,
    val logId: LogId
)

data class MealLogId(
    val breakfast: Meal,
    val lunch: Meal,
    val dinner: Meal,
    val snack: Meal,
    val mealDate: String
)

data class Meal(
    val foodText: List<String>,
    val foodImage: String
)

data class DietLog(
    val name: String,
    val mealLogId: MealLogId
)
