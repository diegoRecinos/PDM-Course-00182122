package com.pdm0126.ex_movies.screens.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm0126.ex_movies.data.repositories.movierepository.MovieApiRepository
import com.pdm0126.ex_movies.data.repositories.movierepository.MovieRepository
import com.pdm0126.ex_movies.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieListViewModel : ViewModel() {
  private val movieRepository: MovieRepository = MovieApiRepository()
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
      _movies.value = movieRepository.getMovies()
      _loading.value = false
    }
  }
}