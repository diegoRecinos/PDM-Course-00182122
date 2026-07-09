package com.pdm0126.ex_rankeuca.data.repository.questionwithoptions


import com.pdm0126.ex_rankeuca.data.model.QuestionWithOptions
import kotlinx.coroutines.flow.Flow

interface QuestionWithOptionsRepository {
    //read: sale de room, son reactivos flow
    fun getQuestionsWithOptions(): Flow<List<QuestionWithOptions>>

    //sincronizar: van a la API y guardan en room
    suspend fun refreshQuestionsWithOptions()
    suspend fun addQuestionWithOptions(title: String)
    suspend fun updateQuestionWithOptions(questionWithOptions: QuestionWithOptions)

}
