package com.pdm0126.ex_rankeuca_room.data.repository

import com.pdm0126.ex_rankeuca_room.data.database.dao.QuestionDao
import com.pdm0126.ex_rankeuca_room.data.database.entity.QuestionEntity
import com.pdm0126.ex_rankeuca_room.data.database.entity.toModel
import com.pdm0126.ex_rankeuca_room.data.model.Question
import com.pdm0126.ex_rankeuca_room.data.model.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class QuestionRepositoryImpl(
    private val questionDao: QuestionDao
) : QuestionRepository {

    override fun getQuestions(): Flow<List<Question>> {
        return questionDao.getQuestionsWithOptions().map { list ->
            list.map { it.toModel() }
        }
    }

    override suspend fun addQuestion(title: String) {
        questionDao.insertQuestion(QuestionEntity(title = title))
    }

    override suspend fun deleteQuestion(question: Question) {
        questionDao.deleteQuestion(question.toEntity())
    }
}
