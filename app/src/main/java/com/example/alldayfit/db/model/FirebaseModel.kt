package com.example.alldayfit.db.model

/*
현 데이터 모델은 real-time firebase 전체 데이터 모델입니다.
*/

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

//{
//    users{
//        userId(firebase Authentication user id){
//            Email : String
//            Name : String
//            Nickname : String
//            Gender : String
//        }
//    }
//    information{
//        userId{
//            exerciseLocate :
//            exerciseTime :
//
//        }
//    }
//    physicalLog{
//        userId(firebase Authentication user id){
//            physicalId(firebaseid) {
//                LogDate : yyyy.MM.dd
//                Height : Int
//                Weight : Int
//                weeklyGoal : List<String>
//            }
//        }
//    }
//    exerciseLog{
//        userId{
//                logId(firebaseid) {
//                    TotalTime : Int
//                    LogDate : yyyy.MM.dd
//                }
//        }
//    }
//    dietLog{
//        userId(firebase Authentication user id) {
//            mealLogId(firebaseid) {
//                Breakfast{
//                    FoodText : [String String String]
//                    FoodImage : String
//                }
//                Lunch{
//                    FoodText : [String String String]
//                    FoodImage : String
//                }
//                Dinner{
//                    FoodText : [String String String]
//                    FoodImage : String
//                }
//                Snack{
//                    FoodText : [String String String]
//                    FoodImage : String
//                }
//                LogDate : yyyy.MM.dd
//            }
//        }
//    }
//    postLog{
//        postId(firebaseid){
//            Author: String
//            PostingDate : String
//            Image : String
//            Content : String
//        }
//    }
//}