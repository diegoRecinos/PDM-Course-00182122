package com.pdm0126.ex_movies.data.api.movies

import com.pdm0126.ex_movies.data.database.entity.MovieEntity
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import com.pdm0126.ex_movies.data.model.Movie


@Serializable
data class MovieDto(
    val id: Int,
    val title: String,
    @SerialName("original_title")
    val originalTitle: String,
    @SerialName("original_language")
    val originalLanguage: String,
    val overview: String,
    @SerialName("release_date")
    val releaseDate: String,
    val adult: Boolean,
    val popularity: Double,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int,
    val video: Boolean,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("poster_path")
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