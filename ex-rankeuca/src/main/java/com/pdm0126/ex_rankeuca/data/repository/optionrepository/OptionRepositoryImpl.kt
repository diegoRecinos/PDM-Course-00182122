package com.pdm0126.ex_rankeuca.data.repository.optionrepository

import android.util.Log
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
import io.ktor.client.request.put
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
            Log.e("OptionRepositoryImpl", "Error al sincronizar opciones: ${e.message}")
           throw e
        }
    }

    //mutar API -> luego local refreshOptions()
    override suspend fun voteOption(optionId: Int) {
        try {
            KtorClient.client.post("vote/$optionId")
            //tras exito, actualizamos Room
            optionDao.incrementVotes(optionId)
        } catch (e: Exception) {
            Log.e("OptionRepositoryImpl", "Error al votar opción: ${e.message}")
            throw e
        }
    }

    override suspend fun resetVotes() {
        KtorClient.client.post("reset")

        optionDao.resetAllVotes()
    }

    override suspend fun createOption(name: String, imageUrl: String?, questionId: Int) {
        //1 llamar API

        try {
            val response = KtorClient.client.post("options") {

                val bodyData = mutableMapOf<String, Any?>(
                    "name" to name,
                    "questionId" to questionId
                )
                // Solo agregamos imageUrl si no es nulo
                if (imageUrl != null) {
                    bodyData["imageUrl"] = imageUrl
                }

                setBody(bodyData)

            }.body<OptionDTO>()

            //guardar resultado real con ID de la API en room
            optionDao.upsertOption(response.toEntity())
        } catch (e: Exception) {
            Log.e("OptionRepositoryImpl", "Error al crear opción: ${e.message}")
            throw e
        }
    }

    override suspend fun updateOption(option: Option) {

        try {
            KtorClient.client.put("options/${option.id}"){
                setBody(option.toEntity())
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

    //Helper privado devuelve DTO
    private suspend fun fetchOptionsFromApi(): List<OptionDTO> =
        KtorClient.client.get("options").body()

}