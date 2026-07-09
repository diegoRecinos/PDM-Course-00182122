package com.pdm0126.ex_rankeuca.data

import android.content.Context
import com.pdm0126.ex_rankeuca.data.database.AppDatabase
import com.pdm0126.ex_rankeuca.data.repository.optionrepository.OptionRepository
import com.pdm0126.ex_rankeuca.data.repository.optionrepository.OptionRepositoryImpl
import com.pdm0126.ex_rankeuca.data.repository.questionrepository.QuestionRepository
import com.pdm0126.ex_rankeuca.data.repository.questionrepository.QuestionRepositoryImpl

class AppProvider(context: Context) {

    private val appDatabase = AppDatabase.getDatabase(context)

    private val questionDao = appDatabase.questionDao()
    private val optionDao = appDatabase.optionDao()

    private val questionRepository: QuestionRepository =
        QuestionRepositoryImpl(questionDao)

    private val optionRepository: OptionRepository =
        OptionRepositoryImpl(optionDao)

    fun provideQuestionRepository(): QuestionRepository {
        return questionRepository
    }

    fun provideOptionRepository(): OptionRepository {
        return optionRepository
    }

    fun provideQuestionWithOptionsRepository(): QuestionWithOptionsRepository {
        return QuestionWithOptionsRepositoryImpl(questionDao, optionDao)
    }

}