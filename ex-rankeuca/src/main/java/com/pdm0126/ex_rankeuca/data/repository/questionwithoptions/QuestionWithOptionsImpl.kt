package com.pdm0126.ex_rankeuca.data.repository.questionwithoptions

import com.pdm0126.ex_rankeuca.data.database.dao.OptionDao
import com.pdm0126.ex_rankeuca.data.database.dao.QuestionDao
import com.pdm0126.ex_rankeuca.data.database.entity.toModel
import com.pdm0126.ex_rankeuca.data.model.Question
import com.pdm0126.ex_rankeuca.data.model.QuestionWithOptions
import com.pdm0126.ex_rankeuca.data.repository.questionrepository.QuestionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class QuestionWithOptionsImpl(
    private val questionDao: QuestionDao,
    private val optionDao: OptionDao
) : QuestionWithOptionsRepository {

//    // read: de Room
//    override fun getQuestions(): Flow<List<Question>> {
//        return questionDao.getQuestionsWithOptions().map { list ->
//            list.map { it.toModel() }
//        }
//    }

    //read de Room
    override fun getQuestionsWithOptions(): Flow<List<QuestionWithOptions>> {
        TODO("NO")
    }

    override suspend fun refreshQuestionsWithOptions() {
        TODO("Not yet implemented")
    }

    override suspend fun addQuestionWithOptions(title: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateQuestionWithOptions(questionWithOptions: QuestionWithOptions) {
        TODO("Not yet implemented")
    }

}