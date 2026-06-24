package com.pdm0126.ex_rankeuca_room.data.api.options

import kotlinx.serialization.Serializable

@Serializable
data class VoteOptionRequestDTO(
    val optionId: Int
)