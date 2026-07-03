package com.pdm0126.ex_rankeuca.data.api.questions

import com.pdm0126.ex_rankeuca.data.database.entity.QuestionEntity
import com.pdm0126.ex_rankeuca.data.model.Question
import kotlinx.serialization.Serializable

@Serializable
data class QuestionDTO(
    val id: Int,
    val title: String
)

// Mappers
fun QuestionDTO.toModel() = Question(
    id = id,
    title = title,
    optionCount = 0
)

fun QuestionDTO.toEntity() = QuestionEntity(
    id = id,
    title = title
)
