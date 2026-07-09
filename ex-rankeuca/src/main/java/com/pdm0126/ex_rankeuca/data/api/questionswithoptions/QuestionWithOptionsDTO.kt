package com.pdm0126.ex_rankeuca.data.api.questionswithoptions

import com.pdm0126.ex_rankeuca.data.api.options.OptionDTO
import com.pdm0126.ex_rankeuca.data.model.Option
import com.pdm0126.ex_rankeuca.data.model.QuestionWithOptions
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseContextualSerialization


@Serializable
class QuestionWithOptionsDTO(
    val id: Int = 0,
    val options: Array<OptionDTO>,
    val text: String,
)