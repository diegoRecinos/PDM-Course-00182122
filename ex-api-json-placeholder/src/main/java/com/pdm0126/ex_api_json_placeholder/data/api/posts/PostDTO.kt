package com.pdm0126.ex_api_json_placeholder.data.api.posts

import com.pdm0126.ex_api_json_placeholder.data.model.Post
import kotlinx.serialization.Serializable

@Serializable
data class PostDTO(
    val userId: Int,
    val id: Int? = null,
    val title: String,
    val body: String
)

//mappers
fun PostDTO.toModel() = Post(
    userId = userId,
    id = id ?: 0,
    title = title,
    body = body
)

fun Post.toDTO() = PostDTO(
    userId = userId,
    id = if (id == 0) null else id,
    title = title,
    body = body
)
