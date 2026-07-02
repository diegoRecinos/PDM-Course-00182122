package com.pdm0126.ex_rankeuca.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pdm0126.ex_rankeuca.data.database.dao.OptionDao
import com.pdm0126.ex_rankeuca.data.database.dao.QuestionDao
import com.pdm0126.ex_rankeuca.data.database.entity.OptionEntity
import com.pdm0126.ex_rankeuca.data.database.entity.QuestionEntity

@Database(
    entities = [QuestionEntity::class, OptionEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun questionDao(): QuestionDao
    abstract fun optionDao(): OptionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context = context.applicationContext,
                    klass = AppDatabase::class.java,
                    name = "rankeuca_database"
                )
                    .fallbackToDestructiveMigration(false)
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}