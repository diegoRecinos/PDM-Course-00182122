package com.pdm0126.ex_api_json_placeholder.ui.screens.screen1

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Screen1(
    viewModel: Screen1ViewModel = viewModel()
    , onBack: () -> Unit) {
    Column {
        Row {
            Button(onClick = { viewModel.fetchPosts() }) { Text("GET Posts") }
            Button(onClick = { viewModel.createPost() }) { Text("POST (Crear)") }
        }

        //load state
        if (viewModel.isLoading) {
            CircularProgressIndicator()
        }

        //error state
        else if (viewModel.errorMessage != null) {
            Text(text = viewModel.errorMessage!!)
        }

        //success state
        else {
            LazyColumn {
                items(viewModel.posts) { post ->
                    Text(text = post.title, style = MaterialTheme.typography.titleMedium)
                    Text(text = post.body)
                    HorizontalDivider()
                }
            }
        }
    }
}