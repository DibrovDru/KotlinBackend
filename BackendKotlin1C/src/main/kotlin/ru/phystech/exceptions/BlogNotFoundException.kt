package ru.phystech.exceptions

class BlogNotFoundException(
    blogId: Long
) : RuntimeException(
    "Blog with id=$blogId not found"
)