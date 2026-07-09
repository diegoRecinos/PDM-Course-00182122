package com.pdm0126.ex_movies.data.repositories.movierepository

import com.pdm0126.ex_movies.data.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieOfflineFirstRepository {
  fun getMovies(): Flow<List<Movie>>
  fun getMovieById(id: Int): Flow<Movie?>

  suspend fun refresh()
  suspend fun refreshMovieById(id: Int)
}