package com.pdm0126.ex_rankeuca_room.data.repository

import com.pdm0126.ex_rankeuca_room.data.model.Option

interface RepositoryInterface {

    suspend fun getOptions(): Result<List<Option>>

    suspend fun voteOption(optionId: Int): Result<Unit>

    suspend fun resetVotes(): Result<Unit>


}