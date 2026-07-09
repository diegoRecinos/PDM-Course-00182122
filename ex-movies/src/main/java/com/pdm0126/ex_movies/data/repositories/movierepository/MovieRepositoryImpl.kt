package com.pdm0126.ex_movies.data.repositories.movierepository

import com.pdm0126.ex_movies.data.api.KtorClient
import com.pdm0126.ex_movies.data.api.movies.MovieDto
import com.pdm0126.ex_movies.data.api.movies.toEntity
import com.pdm0126.ex_movies.data.api.movies.toModel
import com.pdm0126.ex_movies.data.database.dao.MovieDao
import com.pdm0126.ex_movies.data.model.Movie
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.collections.map

import com.pdm0126.ex_movies.data.api.movies.GetMoviesResponseDto

class MovieRepositoryImpl(
  private val dao: MovieDao
): MovieRepository {

  // Room manda: la UI siempre observa esto
  override fun getMovies(): Flow<List<Movie>> =
    dao.getAll().map { list -> list.map { it.toModel() } }

  // El detalle también es reactivo y lee de Room
  override fun getMovieById(id: Int): Flow<Movie?> =
    dao.observeById(id).map { it?.toModel() }

  //sincronizar

// Sincroniza la lista: API -> Room (un solo salto de mapeo)
override suspend fun refresh() {
  val popularMovies = fetchPopular()
  dao.upsertAll(popularMovies.map { it.toEntity() })
}

  // Sincroniza un detalle puntual: API -> Room
  override suspend fun refreshMovieById(id: Int) {
    val movie = fetchById(id)
    dao.upsert(movie.toEntity())
  }

// ─── Helpers privados ───
// En un proyecto real vivirían en un MovieRemoteDataSource inyectado.
// Devuelven DTOs: el repositorio decide cómo persistirlos.

  private suspend fun fetchPopular(): List<MovieDto> =
    KtorClient.client.get("movie/popular") {
      parameter("language", "es-ES")
      parameter("page", 1)
    }.body<GetMoviesResponseDto>().results

  private suspend fun fetchById(id: Int): MovieDto =
    KtorClient.client.get("movie/$id") {
      parameter("language", "es-ES")
    }.body()

}