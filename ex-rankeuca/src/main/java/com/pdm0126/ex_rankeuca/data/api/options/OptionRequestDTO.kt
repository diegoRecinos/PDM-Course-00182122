package com.pdm0126.ex_rankeuca.data.api.options

import kotlinx.serialization.Serializable

@Serializable
data class OptionRequestDTO(
    val name: String,
    val imageUrl: String?,
    val questionId: Int
)