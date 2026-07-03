package com.pdm0126.ex_rankeuca.data.repository.optionrepository

import com.pdm0126.ex_rankeuca.data.api.KtorClient
import com.pdm0126.ex_rankeuca.data.api.options.OptionDTO
import com.pdm0126.ex_rankeuca.data.database.dao.OptionDao
import com.pdm0126.ex_rankeuca.data.database.entity.toEntity
import com.pdm0126.ex_rankeuca.data.database.entity.toModel
import com.pdm0126.ex_rankeuca.data.api.options.toEntity
import com.pdm0126.ex_rankeuca.data.model.Option
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.collections.map

class OptionRepositoryImpl(
    private val optionDao: OptionDao
) : OptionRepository {

    // read: sale de room, son reactivos flow la UI siempre observa esto
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
    // sincronizar: van a la API (DTO) -> Room (Entity)
    // El Flow de arriba se encarga de actualizar la UI automáticamente
    override suspend fun refreshOptions() {
        try {
            val optionsDto = fetchOptionsFromApi()

            optionDao.upsertAllOptions(optionsDto.map { it.toEntity() })

        } catch (e: Exception) {
           throw e
        }
    }

    //mutar API -> luego local refreshOptions()
    override suspend fun voteOption(optionId: Int) {
        KtorClient.client.post("vote/$optionId")
        //tras exito, actualizamos Room
        optionDao.incrementVotes(optionId)
    }

    override suspend fun resetVotes() {
        KtorClient.client.post("reset")

        optionDao.resetAllVotes()
    }

    override suspend fun createOption(name: String, imageUrl: String, questionId: Int) {
        //1 llamar API

        val response = KtorClient.client.post("options") {
            setBody(mapOf("name" to name, "imageUrl" to imageUrl, "questionId" to questionId))
        }.body<OptionDTO>()

        //guardar resultado real con ID de la API en room
        optionDao.upsertOption(response.toEntity())
    }

    override suspend fun updateOption(option: Option) {

        KtorClient.client.post("options/${option.id}")

        optionDao.upsertOption(option.toEntity())
    }

    override suspend fun deleteOption(option: Option) {

        KtorClient.client.delete("options/${option.id}")

        optionDao.deleteOption(option.toEntity())
    }

    //Helper privado devuelve DTO
    private suspend fun fetchOptionsFromApi(): List<OptionDTO> =
        KtorClient.client.get("options").body()

}