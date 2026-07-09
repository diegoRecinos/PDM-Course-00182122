package com.pdm0126.ex_rankeuca.data.repository.optionrepository

import android.util.Log
import com.pdm0126.ex_rankeuca.data.api.KtorClient
import com.pdm0126.ex_rankeuca.data.api.options.OptionDTO
import com.pdm0126.ex_rankeuca.data.api.options.OptionRequestDTO
import com.pdm0126.ex_rankeuca.data.api.options.VoteOptionRequestDTO
import com.pdm0126.ex_rankeuca.data.api.options.toDTO
import com.pdm0126.ex_rankeuca.data.database.dao.OptionDao
import com.pdm0126.ex_rankeuca.data.database.entity.toEntity
import com.pdm0126.ex_rankeuca.data.database.entity.toModel
import com.pdm0126.ex_rankeuca.data.api.options.toEntity
import com.pdm0126.ex_rankeuca.data.model.Option
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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
        try {
            val optionsDto = fetchOptionsFromApi()
            
            // Limpiamos la cache local para no tener datos huérfanos
            optionDao.deleteAllOptions()
            
            // Insertamos los datos frescos del servidor
            optionDao.upsertAllOptions(optionsDto.map { it.toEntity() })

        } catch (e: Exception) {
            Log.e("OptionRepositoryImpl", "Error al sincronizar opciones: ${e.message}")
            throw e
        }
    }

    override suspend fun createOption(name: String, imageUrl: String?, questionId: Int) {
        try {
            val response = KtorClient.client.post("options") {
                setBody(OptionRequestDTO(name, imageUrl, questionId))
            }.body<OptionDTO>()
            optionDao.upsertOption(response.toEntity())
        } catch (e: Exception) {
            Log.e("OptionRepositoryImpl", "Error al crear opción: ${e.message}")
            throw e
        }
    }

    override suspend fun updateOption(option: Option) {
        try {
            KtorClient.client.put("options/${option.id}"){
                setBody(option.toDTO())
            }
            optionDao.upsertOption(option.toEntity())
        } catch (e: Exception) {
            Log.e("OptionRepositoryImpl", "Error al actualizar opción: ${e.message}")
            throw e
        }
    }

    override suspend fun deleteOption(option: Option) {
        try {
            KtorClient.client.delete("options/${option.id}")
            optionDao.deleteOption(option.toEntity())
        } catch (e: Exception) {
            Log.e("OptionRepositoryImpl", "Error al eliminar opción: ${e.message}")
            throw e
        }
    }

    override suspend fun voteOption(optionId: Int) {
        try {
            KtorClient.client.post("vote") {
                setBody(VoteOptionRequestDTO(optionId))
            }
            optionDao.incrementVotes(optionId)
        } catch (e: Exception) {
            Log.e("OptionRepositoryImpl", "Error al votar opción: ${e.message}")
            throw e
        }
    }

    override suspend fun resetVotes() {
        try {
            KtorClient.client.post("reset")
            optionDao.resetAllVotes()
        } catch (e: Exception) {
            throw e
        }
    }

    private suspend fun fetchOptionsFromApi(): List<OptionDTO> =
        KtorClient.client.get("options").body()
}