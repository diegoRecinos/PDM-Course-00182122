package com.pdm0126.ex_rankeuca.data.repository.optionrepository

import com.pdm0126.ex_rankeuca.data.model.Option
import kotlinx.coroutines.flow.Flow

interface OptionRepository {

    //read: sale de room, son reactivos flow
    fun getOptions(): Flow<List<Option>>
    fun getOptions(questionId: Int): Flow<List<Option>>

    //sincronizar: van a la API y guardan en room
    suspend fun refreshOptions()

    //mutar API -> luego refreshOptions()
    suspend fun createOption(name: String, imageUrl: String?, questionId: Int)
    suspend fun updateOption(option: Option)
    suspend fun deleteOption(option: Option)


    suspend fun voteOption(optionId: Int)
    suspend fun resetVotes()
}