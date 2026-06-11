package com.pdm0126.ex_rankeuca_room.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pdm0126.ex_rankeuca_room.data.model.Option

@Entity(tableName = "options")
data class OptionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val imageUrl: String,
    val votes: Int
)

fun OptionEntity.toModel(): Option {
    return Option(
        id = id,
        name = name,
        imageUrl = imageUrl,
        votes = votes
    )
}

fun Option.toEntity(): OptionEntity {
    return OptionEntity(
        id = id,
        name = name,
        imageUrl = imageUrl,
        votes = votes
    )
}