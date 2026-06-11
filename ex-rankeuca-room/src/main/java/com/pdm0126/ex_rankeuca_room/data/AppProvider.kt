package com.pdm0126.ex_rankeuca_room.data

import android.content.Context
import com.pdm0126.ex_rankeuca_room.data.database.AppDatabase
import com.pdm0126.ex_rankeuca_room.data.repository.OptionRepository
import com.pdm0126.ex_rankeuca_room.data.repository.OptionRepositoryImpl

class AppProvider(context: Context) {

    private val appDatabase = AppDatabase.getDatabase(context)
    private val optionDao = appDatabase.optionDao()

    private val optionRepository: OptionRepository =
        OptionRepositoryImpl(optionDao)

    fun provideOptionRepository(): OptionRepository {
        return optionRepository
    }
}