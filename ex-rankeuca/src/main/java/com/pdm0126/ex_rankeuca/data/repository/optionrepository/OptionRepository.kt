package com.pdm0126.ex_rankeuca.data.repository.optionrepository

import com.pdm0126.ex_rankeuca.data.model.Option
import kotlinx.coroutines.flow.Flow

interface OptionRepository {
    fun getOptions(): Flow<List<Option>>
    fun getOptions(questionId: Int): Flow<List<Option>>

    suspend fun addOption(name: String, imageUrl: String, questionId: Int)
    suspend fun updateOption(option: Option)
    suspend fun deleteOption(option: Option)

    suspend fun refreshOptions()
    suspend fun voteOption(optionId: Int)
    suspend fun resetVotes()
}