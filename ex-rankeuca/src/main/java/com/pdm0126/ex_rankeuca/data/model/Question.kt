package com.pdm0126.ex_rankeuca.data.model

import com.pdm0126.ex_rankeuca.data.database.entity.QuestionEntity

data class Question(
    val id: Int = 0,
    val title: String,
    val optionCount: Int = 0,

)

fun Question.toEntity(): QuestionEntity {
    return QuestionEntity(
        id = id,
        title = title,
    )
}