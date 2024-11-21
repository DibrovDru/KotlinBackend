package ru.phystech.dto

import kotlinx.serialization.Serializable
import ru.phystech.model.Post

@Serializable
data class PageDto(
    val posts: List<Post>,
    val hasNext: Boolean,
    val currOffset: Int
)
