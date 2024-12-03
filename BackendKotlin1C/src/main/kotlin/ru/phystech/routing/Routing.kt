package ru.phystech.routing

import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.phystech.dto.PostDto
import ru.phystech.exceptions.BlogNotFoundException
import ru.phystech.service.PostService

fun Application.configureRouting() {
    routing {
        route("/blogs") {
            get("/{id}") {
                val id = call.parameters["id"]?.toLong()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    call.respond(PostService.findPostById(id))
                } catch (ex: BlogNotFoundException) {
                    call.respond(HttpStatusCode.NotFound)
                }
            }

            patch("/{id}") {
                try {
                    val updatedPost = call.receive<PostDto>()
                    val id = call.parameters["id"]?.toLong()
                    if (id == null) {
                        call.respond(HttpStatusCode.BadRequest)
                        return@patch
                    }

                    try {
                        call.respond(PostService.updatePost(id, updatedPost))
                    } catch (ex: BlogNotFoundException) {
                        call.respond(HttpStatusCode.NotFound)
                    } catch (ex: IllegalArgumentException) {
                        call.respond(HttpStatusCode.BadRequest)
                    }
                } catch (ex: IllegalStateException) {
                    call.respond(HttpStatusCode.BadRequest)
                } catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }

            delete("/{id}") {
                val id = call.parameters["id"]?.toLong()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }

                PostService.deletePost(id)
                call.respond(HttpStatusCode.OK)
            }

            get {
                val offset = call.queryParameters["offset"]?.toInt()
                if (offset == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                val limit = call.queryParameters["limit"]?.toInt()
                if (limit == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                try {
                    call.respond(PostService.getPostsPage(offset, limit))
                } catch (ex: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }

            put("/create") {
                val newPost = call.receive<PostDto>()
                call.respond(PostService.createPost(newPost))
            }
        }
    }
}
