package com.pdm0126.ex_mvvm_data_layer_n.data.repositories.MovieRepository

import com.pdm0126.ex_mvvm_data_layer_n.data.repositories.MovieRepository.MovieRepository
import com.pdm0126.ex_mvvm_data_layer_n.dummy.dummyMovies
import com.pdm0126.ex_mvvm_data_layer_n.model.Movie
import kotlinx.coroutines.delay

class MovieApiRepository : MovieRepository {
  override suspend fun getMovies(): List<Movie> {
    delay(2000)
    return dummyMovies
  }

  override suspend fun getMovieById(id: Int): Movie? {
    delay(5000)
    return dummyMovies.find { it.id == id }
  }
}