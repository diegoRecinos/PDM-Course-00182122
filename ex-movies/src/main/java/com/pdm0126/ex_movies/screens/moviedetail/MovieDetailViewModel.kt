package com.pdm0126.ex_movies.screens.moviedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.pdm0126.ex_movies.MoviesAppProviderApp
import com.pdm0126.ex_movies.data.model.Movie
import com.pdm0126.ex_movies.data.repositories.movierepository.MovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _movieId = MutableStateFlow<Int?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val movie: StateFlow<Movie?> = _movieId
        .flatMapLatest { id ->
            if (id == null) MutableStateFlow(null)
            else movieRepository.getMovieById(id)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    fun setMovieId(id: Int) {
        if (_movieId.value == id) return
        _movieId.value = id
        refreshMovie(id)
    }

    private fun refreshMovie(id: Int) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                movieRepository.refreshMovieById(id)
            } catch (e: Exception) {
                _error.value = "Error al actualizar detalle: ${e.message}"
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
                MovieDetailViewModel(repository)
            }
        }
    }
}
