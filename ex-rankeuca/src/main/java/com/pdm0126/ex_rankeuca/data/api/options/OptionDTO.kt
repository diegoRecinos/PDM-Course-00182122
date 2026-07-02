package com.pdm0126.ex_rankeuca.data.api.options

import com.pdm0126.ex_rankeuca.data.model.Option
import kotlinx.serialization.Serializable

@Serializable
data class OptionDTO(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val votes: Int
){

}

//mappers
fun OptionDTO.toModel() = Option(
    id = id,
    value = name,
    imageUrl = imageUrl,
    votes = votes
)

fun Option.toDTO() = OptionDTO(
    id = id,
    name = value,
    imageUrl = imageUrl,
    votes = votes
)
