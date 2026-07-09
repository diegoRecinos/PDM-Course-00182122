package com.pdm0126.ex_movies.data

import android.content.Context
import com.pdm0126.ex_movies.data.database.AppDatabase
import com.pdm0126.ex_movies.data.repositories.movierepository.MovieRepository
import com.pdm0126.ex_movies.data.repositories.movierepository.MovieRepositoryImpl


class AppProvider(context: Context) {

    private val appDatabase = AppDatabase.getDatabase(context)

    private val movieDao = appDatabase.movieDao()

//    private val questionRepository: QuestionRepository =
//        QuestionRepositoryImpl(questionDao)
//
//    private val optionRepository: OptionRepository =
//        OptionRepositoryImpl(optionDao)
//
//    fun provideQuestionRepository(): QuestionRepository {
//        return questionRepository
//    }
//
//    fun provideOptionRepository(): OptionRepository {
//        return optionRepository
//    }

    private val movieRepository: MovieRepository =
        MovieRepositoryImpl(movieDao)

    fun provideMovieRepository(): MovieRepository {
        return movieRepository
    }

}