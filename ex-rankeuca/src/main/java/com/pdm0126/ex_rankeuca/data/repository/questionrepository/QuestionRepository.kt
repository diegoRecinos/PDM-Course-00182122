package com.pdm0126.ex_rankeuca.data.repository.questionrepository

import com.pdm0126.ex_rankeuca.data.model.Question
import kotlinx.coroutines.flow.Flow

interface QuestionRepository {
    //read: sale de room, son reactivos flow
    fun getQuestions(): Flow<List<Question>>

    //sincronizar: van a la API y guardan en room
    suspend fun addQuestion(title: String)
    suspend fun updateQuestion(question: Question)
    suspend fun deleteQuestion(question: Question)
}