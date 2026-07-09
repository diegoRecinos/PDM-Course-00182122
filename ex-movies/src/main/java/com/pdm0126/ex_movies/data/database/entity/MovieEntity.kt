package com.pdm0126.ex_movies.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
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
    val backdropUrl: String,
    val posterUrl: String
)