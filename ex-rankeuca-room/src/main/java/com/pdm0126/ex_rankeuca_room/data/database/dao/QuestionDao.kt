package com.pdm0126.ex_rankeuca_room.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.pdm0126.ex_rankeuca_room.data.database.entity.QuestionEntity
import com.pdm0126.ex_rankeuca_room.data.database.entity.QuestionWithOptions

@Dao
interface QuestionDao {

    @Transaction
    @Query("SELECT * FROM questions")
    fun getQuestionsWithOptions(): Flow<List<QuestionWithOptions>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: QuestionEntity)

    @Delete
    suspend fun deleteQuestion(question: QuestionEntity)
}