package com.pdm0126.ex_api_json_placeholder.data.api.posts

import kotlinx.serialization.Serializable

@Serializable
data class CreatePostRequestDto(
    val title: String,
    val body: String,
    val userId: Int
)