package com.pdm0126.ex_api_json_placeholder.ui.screens.screen1

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm0126.ex_api_json_placeholder.data.api.KtorClient
import com.pdm0126.ex_api_json_placeholder.data.model.Post
import com.pdm0126.ex_api_json_placeholder.repository.PostRepository.PostApiRepository
import com.pdm0126.ex_api_json_placeholder.repository.PostRepository.PostRepository
import kotlinx.coroutines.launch

class Screen1ViewModel: ViewModel() {

    private val repository: PostRepository = PostApiRepository(KtorClient.client)

    var posts by mutableStateOf<List<Post>>(emptyList())
    var isLoading by mutableStateOf(false)

    //estado para guardar mensaje de error
    var errorMessage by mutableStateOf<String?>(null)

    // Para el GET
    fun fetchPosts() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            repository.getPosts()
                .onSuccess { data ->
                    posts = data
                }
                .onFailure { e ->
                    errorMessage = e.message
                }
            isLoading = false
        }
    }

    // Para el POST
    fun createPost() {
        viewModelScope.launch {

            isLoading = true
            errorMessage = null

            repository.createPost(
                title = "title Post",
                body = " POST body via Create Post() "
            )
                .onSuccess { data ->
                    posts = listOf(data) + posts
                }
                .onFailure { e ->
                    errorMessage = e.message
                }
            isLoading = false


//            try {
//                val newPost = Post(
//                    userId = 1,
//                    id = 0,
//                    title = "title Post",
//                    body = " POST body created with POST "
//                )
//                val result = repository.createPost(newPost)
//                // Agregamos el resultado al inicio de la lista para verlo en pantalla
//                posts = listOf(result) + posts
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
        }
    }
}
