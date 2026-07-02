package com.pdm0126.ex_movies.data.repositories.movierepository

import com.pdm0126.ex_movies.model.Movie

interface MovieRepository {
  suspend fun getMovies(): List<Movie>
  suspend fun getMovieById(id: Int): Movie?
}