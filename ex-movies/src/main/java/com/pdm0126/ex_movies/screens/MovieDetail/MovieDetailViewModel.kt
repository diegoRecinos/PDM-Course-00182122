package com.pdm0126.ex_mvvm_data_layer_n.screens.MovieDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm0126.ex_movies.data.repositories.movierepository.MovieRepositoryImpl
import com.pdm0126.ex_movies.data.repositories.movierepository.MovieRepository
import com.pdm0126.ex_movies.data.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel : ViewModel() {
  private val movieRepository: MovieRepository = MovieRepositoryImpl()

  private val _movie = MutableStateFlow<Movie?>(null)
  val movie = _movie.asStateFlow()

  private val _loading = MutableStateFlow<Boolean>(false)
  val loading = _loading.asStateFlow()

  fun loadMovieById(id: Int) {
    viewModelScope.launch {
      _loading.value = true
      _movie.value = movieRepository.getMovieById(id)
      _loading.value = false
    }
  }
}