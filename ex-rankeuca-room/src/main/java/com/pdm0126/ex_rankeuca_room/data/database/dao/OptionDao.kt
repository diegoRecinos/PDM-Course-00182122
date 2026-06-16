package com.pdm0126.ex_rankeuca_room.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pdm0126.ex_rankeuca_room.data.database.entity.OptionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OptionDao {

//    @Query("SELECT * FROM options")
//    fun getAllOptions(): Flow<List<OptionEntity>>
    @Query("SELECT * FROM options WHERE questionId = :questionId")
    fun getOptionsForQuestion(questionId: Int): Flow<List<OptionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOption(option: OptionEntity)

    @Delete
    suspend fun deleteOption(option: OptionEntity)

    @Query("UPDATE options SET votes = votes + 1 WHERE id = :optionId")
    suspend fun incrementVotes(optionId: Int)

    @Query("UPDATE options SET votes = 0")
    suspend fun resetAllVotes()
}