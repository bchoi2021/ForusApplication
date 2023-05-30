package com.example.qa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

object QuestionData {

    fun getQuestion(): ArrayList<Question>{

        val queList: ArrayList<Question> = arrayListOf()

        val q1 = Question(
            1,
            "천연자원들을 다시 환경으로 돌려보내는 것은?",
            "비치코밍",
            "제로웨이스트",
            "플로깅",
            "프리사이클링",
            2
        )

        val q2 = Question(
            1,
            "제품 및 서비스의 소비를 통해 자신의 신념이나 가치를 표현하는 행위는?",
            "칩시크소비",
            "그린슈머",
            "미닝아웃소비",
            "럭셔리소비",
            3
        )

        val q3 = Question(
            1,
            "세계환경의 날은 언제일까요?",
            "4월 5일",
            "7월 5일",
            "6월 5일",
            "8월 5일",
            3
        )

        queList.add(q1)
        queList.add(q2)
        queList.add(q3)

        return queList
    }
}