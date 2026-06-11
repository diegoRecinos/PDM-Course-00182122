package com.pdm0126.ex_api_json_placeholder.repository.PostRepository

import com.pdm0126.ex_api_json_placeholder.data.model.Post

interface PostRepository {

    suspend fun getPosts(): Result<List<Post>>
    suspend fun createPost(title: String, body: String): Result<Post>

}