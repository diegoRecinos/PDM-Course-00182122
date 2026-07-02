package com.pdm0126.ex_rankeuca_room.data.repository

import com.pdm0126.ex_rankeuca.data.database.dao.OptionDao
import com.pdm0126.ex_rankeuca.data.database.entity.toEntity
import com.pdm0126.ex_rankeuca.data.database.entity.toModel
import kotlinx.coroutines.flow.map
import com.pdm0126.ex_rankeuca.data.model.Option
import kotlinx.coroutines.flow.Flow

class OptionRepositoryImpl(
    private val optionDao: OptionDao
) : OptionRepository {

    override fun getOptions(): Flow<List<Option>> {
        return optionDao.getAllOptions().map { entities ->
            entities.map { it.toModel() }
        }
    }

    override fun getOptions(questionId: Int): Flow<List<Option>> {
        return optionDao.getOptionsForQuestion(questionId).map { entities ->
            entities.map { it.toModel() }
        }
    }

    override suspend fun refreshOptions() {
        // Deshabilitado: Solo Room local
    }

    override suspend fun voteOption(optionId: Int) {
        optionDao.incrementVotes(optionId)
    }

    override suspend fun resetVotes() {
        optionDao.resetAllVotes()
    }

    override suspend fun addOption(name: String, imageUrl: String, questionId: Int) {
        val option = Option(
            value = name,
            imageUrl = imageUrl,
            questionId = questionId,
            votes = 0
        )
        optionDao.insertOption(option.toEntity())
    }

    override suspend fun deleteOption(option: Option) {
        optionDao.deleteOption(option.toEntity())
    }
}
