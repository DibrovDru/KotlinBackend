package ru.phystech.service

import org.slf4j.LoggerFactory
import ru.phystech.dao.PostDao
import ru.phystech.dto.PageDto
import ru.phystech.dto.PostDto
import ru.phystech.exceptions.BlogNotFoundException
import ru.phystech.model.Post

object PostService {
    private val LOG = LoggerFactory.getLogger(PostService::class.java)

    fun findPostById(id: Long): Post {
        val blog = PostDao.findPostById(id) ?: throw BlogNotFoundException(id)
        LOG.info("Found post with id=$id")
        return blog
    }

    fun updatePost(id: Long, postDto: PostDto): Post {
        validateBlogData(postDto)
        val blog = findPostById(id)
        LOG.info("Updating post with id=${blog.id}. New data: $postDto")
        return PostDao.updatePost(id, postDto)
    }

    fun createPost(postDto: PostDto): Post {
        validateBlogData(postDto)
        LOG.info("Creating new post: $postDto")
        return PostDao.insertPost(postDto)
    }

    fun getPostsPage(offset: Int, limit: Int): PageDto {
        require(offset >= 0) { "Offset should be non negative number" }
        require(limit > 0) { "Limit should be positive number" }
        val posts = PostDao.getPosts(limit + 1, offset)
        val hasNext = posts.size == limit + 1
        return PageDto(
            if (hasNext) posts.dropLast(1) else posts,
            hasNext,
            offset + posts.size
        )
    }

    fun deletePost(id: Long) {
        PostDao.deletePost(id)
        LOG.info("Post with id=$id deleted successfully")
    }

    private fun validateBlogData(postDto: PostDto) {
        require(postDto.text.length <= 500) { "Post is too long" }
    }
}
