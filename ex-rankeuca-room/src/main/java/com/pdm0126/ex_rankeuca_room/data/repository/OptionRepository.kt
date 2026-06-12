package com.pdm0126.ex_rankeuca_room.data.repository

import com.pdm0126.ex_rankeuca_room.data.model.Option
import kotlinx.coroutines.flow.Flow

interface OptionRepository {
    fun getOptions(): Flow<List<Option>>
    suspend fun addOption(option: Option)
    suspend fun deleteOption(option: Option)

    suspend fun refreshOptions()
    suspend fun voteOption(optionId: Int)
    suspend fun resetVotes()
}