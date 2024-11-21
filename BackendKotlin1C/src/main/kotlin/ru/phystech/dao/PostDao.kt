package ru.phystech.dao

import io.ktor.util.collections.*
import ru.phystech.model.Post
import ru.phystech.dto.PostDto
import java.time.LocalDateTime
import java.util.concurrent.atomic.AtomicLong
import kotlin.math.min

object PostDao {
    private val currentId = AtomicLong(0)
    private val POSTS_MAP = ConcurrentMap<Long, Post>()

    fun findPostById(id: Long): Post? {
        return POSTS_MAP[id]
    }

    fun getPosts(limit: Int, offset: Int): List<Post> {
        val posts = POSTS_MAP.values.sortedByDescending { it.id }
        return posts.subList(offset, min(posts.size, offset + limit))
    }

    fun insertPost(post: PostDto): Post {
        val creationDateTime = LocalDateTime.now()
        val id = nextPostId()
        val result = Post(
            id = id,
            text = post.text,
            createdAt = creationDateTime,
            updatedAt = creationDateTime
        )

        require(!POSTS_MAP.containsKey(id)) { "Post with id=$id already existing in db" }
        POSTS_MAP[id] = result
        return result.copy()
    }

    fun updatePost(id: Long, updatedPost: PostDto): Post {
        val postToUpdate = POSTS_MAP[id]!!
        POSTS_MAP[id] = postToUpdate.copy(
            updatedAt = LocalDateTime.now(),
            text = updatedPost.text
        )

        return POSTS_MAP[id]!!.copy()
    }

    fun deletePost(id: Long) {
        POSTS_MAP.remove(id)
    }

    private fun nextPostId(): Long {
        return currentId.addAndGet(1)
    }
}