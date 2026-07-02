package com.pdm0126.ex_movies.screens.movielist

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pdm0126.ex_movies.components.AppScaffold
import com.pdm0126.ex_movies.components.MovieItem

@Composable
fun MovieListScreen(
  navigateToDetail: (Int) -> Unit,
  viewModel: MovieListViewModel = viewModel()
) {

  val movies by viewModel.movies.collectAsState()
  val loading by viewModel.loading.collectAsState()

  if (loading) {
    AppScaffold(title = "Movies") { padding ->
      CircularProgressIndicator(modifier = Modifier.padding(padding))
    }
    return
  }

  AppScaffold(title = "Movies") { padding ->
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(padding)
        .padding(16.dp),
    ) {
      items(movies) { movie ->
        MovieItem(
          movie = movie,
          onClick = { navigateToDetail(movie.id) }
        )
        Spacer(modifier = Modifier.height(12.dp))
      }
    }
  }
}
