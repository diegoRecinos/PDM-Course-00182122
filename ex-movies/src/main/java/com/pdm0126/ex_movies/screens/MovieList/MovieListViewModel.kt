package com.pdm0126.ex_mvvm_data_layer_n.screens.MovieList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm0126.ex_movies.data.repositories.movierepository.MovieApiOfflineFirstRepository
import com.pdm0126.ex_movies.data.repositories.movierepository.MovieOfflineFirstRepository
import com.pdm0126.ex_movies.data.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieListViewModel : ViewModel() {
  private val movieOfflineFirstRepository: MovieOfflineFirstRepository = MovieApiOfflineFirstRepository()
  private val _movies = MutableStateFlow<List<Movie>>(emptyList())
  val movies = _movies.asStateFlow()

  private val _loading = MutableStateFlow<Boolean>(false)
  val loading = _loading.asStateFlow()

  init {
    loadMovies()
  }

  fun loadMovies() {
    viewModelScope.launch {
      _loading.value = true
      _movies.value = movieOfflineFirstRepository.getMovies()
      _loading.value = false
    }
  }
}