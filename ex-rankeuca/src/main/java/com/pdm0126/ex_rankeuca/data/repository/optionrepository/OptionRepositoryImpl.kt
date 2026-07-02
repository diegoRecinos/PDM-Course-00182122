package com.pdm0126.ex_rankeuca.data.repository.optionrepository

import com.pdm0126.ex_rankeuca.data.database.dao.OptionDao
import com.pdm0126.ex_rankeuca.data.database.entity.toEntity
import com.pdm0126.ex_rankeuca.data.database.entity.toModel
import com.pdm0126.ex_rankeuca.data.model.Option
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.collections.map

class OptionRepositoryImpl(
    private val optionDao: OptionDao
) : OptionRepository {

    // read: sale de room, son reactivos flow
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

    // sincronizar: van a la API y guardan en room
    override suspend fun refreshOptions() {

    }

    override suspend fun voteOption(optionId: Int) {
        optionDao.incrementVotes(optionId)
    }

    override suspend fun resetVotes() {
        optionDao.resetAllVotes()
    }

    override suspend fun createOption(name: String, imageUrl: String, questionId: Int) {
        val option = Option(
            value = name,
            imageUrl = imageUrl,
            questionId = questionId,
            votes = 0
        )
        optionDao.insertOption(option.toEntity())
    }

    override suspend fun updateOption(option: Option) {
        optionDao.updateOption(option.toEntity())
    }

    override suspend fun deleteOption(option: Option) {
        optionDao.deleteOption(option.toEntity())
    }
}