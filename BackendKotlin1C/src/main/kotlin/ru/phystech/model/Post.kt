package ru.phystech.model

import kotlinx.serialization.Serializable
import ru.phystech.serializers.LocalDateTimeSerializer
import java.time.LocalDateTime

@Serializable
data class Post(
    val id: Long,
    val text: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime,
    @Serializable(with = LocalDateTimeSerializer::class)
    val updatedAt: LocalDateTime
)
