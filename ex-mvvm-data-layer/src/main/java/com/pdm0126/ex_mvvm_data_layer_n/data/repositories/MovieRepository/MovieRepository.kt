package com.pdm0126.ex_mvvm_data_layer_n.data.repositories.MovieRepository

import com.pdm0126.ex_mvvm_data_layer_n.model.Movie

interface MovieRepository {
  suspend fun getMovies(): List<Movie>
  suspend fun getMovieById(id: Int): Movie?
}