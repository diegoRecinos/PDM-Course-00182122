package com.pdm0126.ex_rankeuca_room.data.repository

import com.pdm0126.ex_rankeuca_room.data.database.dao.OptionDao
import com.pdm0126.ex_rankeuca_room.data.database.entity.toEntity
import com.pdm0126.ex_rankeuca_room.data.database.entity.toModel
import kotlinx.coroutines.flow.map
import com.pdm0126.ex_rankeuca_room.data.model.Option
import kotlinx.coroutines.flow.Flow

class OptionRepositoryImpl(
    private val optionDao: OptionDao
) : OptionRepository {

    override fun getOptions(): Flow<List<Option>> {
        return optionDao.getAllOptions().map { entities ->
            entities.map { it.toModel() }
        }
    }

    override suspend fun addOption(option: Option) {
        optionDao.insertOption(option.toEntity())
    }

    override suspend fun deleteOption(option: Option) {
        optionDao.deleteOption(option.toEntity())
    }

    override suspend fun voteOption(optionId: Int) = optionDao.incrementVotes(optionId)
    override suspend fun resetVotes() = optionDao.resetAllVotes()
}