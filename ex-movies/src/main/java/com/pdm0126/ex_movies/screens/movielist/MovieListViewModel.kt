package com.pdm0126.ex_movies.screens.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.pdm0126.ex_movies.MoviesAppProviderApp
import com.pdm0126.ex_movies.data.repositories.movierepository.MovieRepository
import com.pdm0126.ex_movies.data.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val movies: StateFlow<List<Movie>> = movieRepository.getMovies()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _loading = MutableStateFlow(false)
    val isRefreshing = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    init {
        refreshMovies()
    }

    fun refreshMovies() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                movieRepository.refresh()
            } catch (e: Exception) {
                _error.value = "Error al actualizar películas: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MoviesAppProviderApp)
                val repository = application.appProvider.provideMovieRepository()
                MovieListViewModel(repository)
            }
        }
    }
}
