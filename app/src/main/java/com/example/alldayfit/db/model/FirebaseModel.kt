package com.example.alldayfit.db.model



data class FirebaseModel(
    val user: UserData,
    val physicalData: PhysicalData,
    val exerciseRecord: ExerciseRecord,
    val dietRecord: DietRecord,
    val post: Post
) {
    data class UserData(
        val email: String,
        val name: String,
        val nickname: String,
    )

    data class PhysicalData(
        val inputDate: String,
        val height: Int,
        val weight: Int,
    )

    data class ExerciseRecord(
        val totalTime: Long,
        val logDate: String,
    )

    data class DietRecord(
        val breakfast: Meal,
        val lunch: Meal,
        val dinner: Meal,
        val snack: Meal,
        val logDate: String,
    ) {
        data class Meal(
            val foodText: List<String>,
            val foodImage: String,
        )
    }

    data class Post(
        val postId: String,
        val title: String,
        val postingDate: String,
        val nickname: String,
        val content: String,
    )

}
