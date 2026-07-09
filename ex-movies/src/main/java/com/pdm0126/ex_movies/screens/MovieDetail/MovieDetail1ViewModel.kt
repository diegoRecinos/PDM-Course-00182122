package com.pdm0126.ex_mvvm_data_layer_n.screens.MovieDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm0126.ex_movies.data.repositories.movierepository.MovieApiOfflineFirstRepository
import com.pdm0126.ex_movies.data.repositories.movierepository.MovieOfflineFirstRepository
import com.pdm0126.ex_movies.data.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetail1ViewModel : ViewModel() {
    private val movieOfflineFirstRepository: MovieOfflineFirstRepository = MovieApiOfflineFirstRepository()

    private val _movie = MutableStateFlow<Movie?>(null)

    val movie = _movie.asStateFlow()

    private val _loading = MutableStateFlow<Boolean>(false)

    val loading = _loading.asStateFlow()

    fun loadMovie(movieId: Int) {
        viewModelScope.launch {
            _loading.value = true
            _movie.value = movieOfflineFirstRepository.getMovieById(movieId)
            _loading.value = false
        }
    }

}