package com.pdm0126.ex_rankeuca.data.api.options

import kotlinx.serialization.Serializable

@Serializable
data class VoteResponseDTO(
    val ok: Boolean,
    val message: String? = null
)
