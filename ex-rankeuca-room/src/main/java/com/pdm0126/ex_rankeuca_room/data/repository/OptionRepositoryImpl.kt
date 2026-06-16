package com.pdm0126.ex_rankeuca_room.data.repository

import com.pdm0126.ex_rankeuca_room.data.api.KtorClient
import com.pdm0126.ex_rankeuca_room.data.database.dao.OptionDao
import com.pdm0126.ex_rankeuca_room.data.database.entity.toEntity
import com.pdm0126.ex_rankeuca_room.data.database.entity.toModel
import kotlinx.coroutines.flow.map
import com.pdm0126.ex_rankeuca_room.data.model.Option
import kotlinx.coroutines.flow.Flow

class OptionRepositoryImpl(
    private val optionDao: OptionDao,
    private val apiRepository: ApiRepository
) : OptionRepository {

//    //1 flujo vivo la ui siempre lo mira
//    override fun getOptions(): Flow<List<Option>> {
//        //devuelve en vivo la lista de opciones
//        return optionDao.getAllOptions().map { entities ->
//            entities.map { it.toModel() }
//        }
//    }
    override fun getOptions(questionId: Int): Flow<List<Option>> {
        return optionDao.getOptionsForQuestion(questionId).map { entities ->
            entities.map { it.toModel() }
        }
    }
    override suspend fun refreshOptions() {
        // 1. Pedimos a la API
        val result = apiRepository.getOptions()
        // 2. Actualizamos Room
        result.onSuccess { remoteOptions ->
            remoteOptions.forEach { option ->
                optionDao.insertOption(option.toEntity())
            }
        }
    }



    override suspend fun voteOption(optionId: Int) {
        // Votamos en la API primero
        apiRepository.voteOption(optionId).onSuccess {
            // Si la API confirmó, actualizamos localmente para que la UI reaccione
            refreshOptions()
        }
    }

    override suspend fun resetVotes() {
        apiRepository.resetVotes().onSuccess {
            refreshOptions()
        }
    }

//    override suspend fun addOption(option: Option) {
//        optionDao.insertOption(option.toEntity())
//    }
    override suspend fun addOption(name: String, imageUrl: String, questionId: Int) {
        val option = Option(name = name, imageUrl = imageUrl, questionId = questionId)
        optionDao.insertOption(option.toEntity())
    }

    override suspend fun deleteOption(option: Option) {
        optionDao.deleteOption(option.toEntity())
    }
}