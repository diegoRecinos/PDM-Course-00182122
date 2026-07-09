package com.pdm0126.ex_movies.screens.movielist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pdm0126.ex_movies.components.AppScaffold
import com.pdm0126.ex_movies.components.MovieItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(
  navigateToDetail: (Int) -> Unit,
  viewModel: MovieListViewModel = viewModel(factory = MovieListViewModel.Factory)
) {

  val movies by viewModel.movies.collectAsState()
  val isRefreshing by viewModel.isRefreshing.collectAsState()
  val error by viewModel.error.collectAsState()

  AppScaffold(title = "Movies") { padding ->
    Box(modifier = Modifier.fillMaxSize().padding(padding)) {

      if (movies.isEmpty() && isRefreshing) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

      } else if (error != null && movies.isEmpty()) {
          Text(text = error!!, modifier = Modifier.align(Alignment.Center))
      } else {

        PullToRefreshBox (
          isRefreshing = isRefreshing,
          onRefresh = { viewModel.refreshMovies() },
          modifier = Modifier.fillMaxSize()
        ) {
              LazyColumn(
                modifier = Modifier
                  .fillMaxSize()
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
    }
  }
}
