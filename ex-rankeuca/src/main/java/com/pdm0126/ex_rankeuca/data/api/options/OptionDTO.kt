package com.pdm0126.ex_rankeuca.data.api.options

import com.pdm0126.ex_rankeuca.data.database.entity.OptionEntity
import com.pdm0126.ex_rankeuca.data.model.Option
import kotlinx.serialization.Serializable

@Serializable
data class OptionDTO(
    val id: Int,
    val name: String,
    val imageUrl: String?,
    val votes: Int,
    val questionId: Int
){

}

//mappers
fun OptionDTO.toModel() = Option(
    id = id,
    value = name,
    imageUrl = imageUrl,
    votes = votes,
    questionId = questionId
)

fun Option.toDTO() = OptionDTO(
    id = id,
    name = value,
    imageUrl = imageUrl,
    votes = votes,
    questionId = questionId
)


fun OptionDTO.toEntity() = OptionEntity(
    id = id,
    name = name,
    imageUrl = imageUrl,
    votes = votes,
    questionId = questionId
)

