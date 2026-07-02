package com.pdm0126.ex_movies.screens.moviedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm0126.ex_movies.data.repositories.movierepository.MovieApiRepository
import com.pdm0126.ex_movies.data.repositories.movierepository.MovieRepository
import com.pdm0126.ex_movies.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetail1ViewModel : ViewModel() {
    private val movieRepository: MovieRepository = MovieApiRepository()

    private val _movie = MutableStateFlow<Movie?>(null)

    val movie = _movie.asStateFlow()

    private val _loading = MutableStateFlow<Boolean>(false)

    val loading = _loading.asStateFlow()

    fun loadMovie(movieId: Int) {
        viewModelScope.launch {
            _loading.value = true
            _movie.value = movieRepository.getMovieById(movieId)
            _loading.value = false
        }
    }

}