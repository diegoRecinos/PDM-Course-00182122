package com.pdm0126.ex_rankeuca.data.repository

import com.pdm0126.ex_rankeuca.data.model.Option

interface RepositoryInterface {

    suspend fun getOptions(): Result<List<Option>>

    suspend fun voteOption(optionId: Int): Result<Unit>

    suspend fun resetVotes(): Result<Unit>


}