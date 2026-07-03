package com.pdm0126.ex_rankeuca.data.repository.questionrepository

import com.pdm0126.ex_rankeuca.data.api.KtorClient
import com.pdm0126.ex_rankeuca.data.api.questions.QuestionDTO
import com.pdm0126.ex_rankeuca.data.api.questions.toEntity
import com.pdm0126.ex_rankeuca.data.database.dao.QuestionDao
import com.pdm0126.ex_rankeuca.data.database.entity.QuestionEntity
import com.pdm0126.ex_rankeuca.data.database.entity.toModel
import com.pdm0126.ex_rankeuca.data.model.Question
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class QuestionRepositoryImpl(
    private val questionDao: QuestionDao
) : QuestionRepository {

    // read: de Room
    override fun getQuestions(): Flow<List<Question>> {
        return questionDao.getQuestionsWithOptions().map { list ->
            list.map { it.toModel() }
        }
    }

    // sincronizar: API -> Room
    override suspend fun refreshQuestions() {
        try {
            val questionsDto = fetchQuestionsFromApi()

            questionDao.upsertAllQuestions(questionsDto.map { it.toEntity() })
        } catch (e: Exception) {

            throw e
        }
    }

    // mutar: API -> Room
    override suspend fun addQuestion(title: String) {
        try {
            val response = KtorClient.client.post("questions") {
                setBody(mapOf("title" to title))
            }.body<QuestionDTO>()

            questionDao.upsertQuestion(response.toEntity())
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun updateQuestion(question: Question) {
        try {
            KtorClient.client.post("questions/${question.id}") {
                setBody(question)
            }
            questionDao.upsertQuestion(QuestionEntity(id = question.id, title = question.title))
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun deleteQuestion(question: Question) {
        try {
            KtorClient.client.post("questions/${question.id}")
            questionDao.deleteQuestion(QuestionEntity(id = question.id, title = question.title))
        } catch (e: Exception) {
            throw e
        }
    }

    // ─── Helpers privados ───
    private suspend fun fetchQuestionsFromApi(): List<QuestionDTO> =
        KtorClient.client.get("questions").body()

}