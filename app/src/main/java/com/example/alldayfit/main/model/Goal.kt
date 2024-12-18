package com.example.alldayfit.main.model

data class Goal(
    val goals: String = "",  // 기본값을 설정하여 Firebase에서 인식 가능하게 만듭니다.
    var goalCheck: Boolean = false,
    val type: Int = POST_POSITION
) {
    companion object {
        const val POST_POSITION = 0
        const val DIALOG_POSITION = 1
    }

    // Firebase에서 객체 역직렬화 할 때 필요한 기본 생성자
    constructor() : this("", false, POST_POSITION)
}