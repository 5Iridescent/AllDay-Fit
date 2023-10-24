package com.example.alldayfit.db.model

/*
현 데이터 모델은 real-time firebase 전체 데이터 모델입니다.
*/

data class FirebaseModel(
    val user: UserData,
    val physicalData: PhysicalData,
    val exerciseRecord: ExerciseRecord,
    val dietRecord: DietRecord,
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
        val totalTime: Int,
        val exerciseDate: String,
    )

    data class DietRecord(
        val breakfast: Meal,
        val lunch: Meal,
        val dinner: Meal,
        val snack: Meal,
        val mealDate: String,
    ) {
        data class Meal(
            val FoodText: List<String>,
            val FoodImage: String,
        )
    }

}

//{
//    users{
//    userId{
//        email : String
//        name : String
//        nickname : String
//    }
//}
//    physicalInformation{
//    userId{
//        {
//            informationId {
//                inputDate : yyyy.MM.dd
//                Height : Int
//                Weight : Int
//            }
//        }
//    }
//}
//    exerciseLog{
//    userId{
//        {
//            logId {
//                TotalTime : Int
//                exerciseDate : yyyy.MM.dd
//            }
//        }
//    }
//}
//    dietLog{
//    userId{
//        {
//            meallogId {
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
//                mealDate : yyyy.MM.dd
//            }
//        }
//    }
//}
//}