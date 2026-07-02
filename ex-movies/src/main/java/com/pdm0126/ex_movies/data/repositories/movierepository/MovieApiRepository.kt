package com.pdm0126.ex_movies.data.repositories.movierepository

import com.pdm0126.ex_movies.dummy.dummyMovies
import com.pdm0126.ex_movies.model.Movie
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