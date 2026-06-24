package com.pdm0126.ex_rankeuca_room.data.database.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.pdm0126.ex_rankeuca_room.data.model.Question

data class QuestionWithOptions(
    @Embedded val question: QuestionEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "questionId"
    )
    val options: List<OptionEntity>
)

fun QuestionWithOptions.toModel(): Question {
    return Question(
        id = question.id,
        title = question.title,
        optionCount = options.size,
    )
}