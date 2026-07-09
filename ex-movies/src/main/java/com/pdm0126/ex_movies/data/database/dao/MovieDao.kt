package com.pdm0126.ex_movies.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.pdm0126.ex_movies.data.database.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    // Leer: reactivo, la UI observa esto
    @Query("SELECT * FROM movies ORDER BY popularity DESC")
    fun getAll(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE id = :id")
    fun observeById(id: Int): Flow<MovieEntity?>

    // Guardar: insertar o actualizar sin duplicar
    @Upsert
    suspend fun upsert(movie: MovieEntity)

    @Upsert
    suspend fun upsertAll(movies: List<MovieEntity>)
}