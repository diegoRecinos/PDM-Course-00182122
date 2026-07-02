package com.pdm0126.ex_rankeuca.data.repository

import com.pdm0126.ex_rankeuca.data.model.Question
import kotlinx.coroutines.flow.Flow

interface QuestionRepository {
    fun getQuestions(): Flow<List<Question>>
    suspend fun addQuestion(title: String)
    suspend fun updateQuestion(question: Question)
    suspend fun deleteQuestion(question: Question)
}