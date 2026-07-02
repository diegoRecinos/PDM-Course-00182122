package com.pdm0126.ex_rankeuca.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.pdm0126.ex_rankeuca.data.model.Option

@Entity(
    tableName = "options",
    foreignKeys = [
        ForeignKey(
            entity = QuestionEntity::class,
            parentColumns = ["id"],
            childColumns = ["questionId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("questionId")]
)
data class OptionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val imageUrl: String,
    val votes: Int,
    val questionId: Int
)

fun OptionEntity.toModel(): Option {
    return Option(
        id = id,
        value = name,
        imageUrl = imageUrl,
        votes = votes,
        questionId = questionId
    )
}

fun Option.toEntity(): OptionEntity {
    return OptionEntity(
        id = id,
        name = value,
        imageUrl = imageUrl,
        votes = votes,
        questionId = questionId
    )
}