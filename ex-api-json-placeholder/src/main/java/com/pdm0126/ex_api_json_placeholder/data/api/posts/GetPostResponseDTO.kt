package com.pdm0126.ex_api_json_placeholder.data.api.posts

import kotlinx.serialization.Serializable


@Serializable
data class GetPostResponseDTO(
    val page: Int? = null,
    val results: List<PostDTO>,
)