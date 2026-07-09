package com.pdm0126.ex_movies.data.api.movies

import com.pdm0126.ex_movies.data.database.entity.MovieEntity
import kotlinx.serialization.Serializable
import com.pdm0126.ex_movies.data.model.Movie


@Serializable
data class MovieDto(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val originalLanguage: String,
    val overview: String,
    val releaseDate: String,
    val adult: Boolean,
    val popularity: Double,
    val voteAverage: Double,
    val voteCount: Int,
    val video: Boolean,
    val backdropPath: String? = null,
    val posterPath: String? = null
)

//mappers
fun MovieEntity.toModel(): Movie = Movie(
    id = id,
    title = title,
    originalTitle = originalTitle,
    originalLanguage = originalLanguage,
    overview = overview,
    releaseDate = releaseDate,
    adult = adult,
    genreIds = emptyList(),   // no lo guardamos en Room (ver nota arriba)
    popularity = popularity,
    voteAverage = voteAverage,
    voteCount = voteCount,
    video = video,
    backdropUrl = backdropUrl,
    posterUrl = posterUrl
)

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

fun MovieDto.toEntity(): MovieEntity = MovieEntity(
    id = id,
    title = title,
    originalTitle = originalTitle,
    originalLanguage = originalLanguage,
    overview = overview,
    releaseDate = releaseDate,
    adult = adult,
    popularity = popularity,
    voteAverage = voteAverage,
    voteCount = voteCount,
    video = video,
    backdropUrl = backdropPath?.let { "$IMAGE_BASE_URL$it" } ?: "",
    posterUrl = posterPath?.let { "$IMAGE_BASE_URL$it" } ?: ""
)