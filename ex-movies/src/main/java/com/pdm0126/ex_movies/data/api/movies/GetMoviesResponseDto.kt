package com.pdm0126.ex_movies.data.api.movies

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetMoviesResponseDto(
    val page: Int = 0,
    val results: List<MovieDto> = emptyList(),
    @SerialName("total_pages") val totalPages: Int = 0,
    @SerialName("total_results") val totalResults: Int = 0,
    @SerialName("page_results") val pageResults: Int? = null
)