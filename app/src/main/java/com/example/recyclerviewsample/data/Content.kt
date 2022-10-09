package com.example.recyclerviewsample.data

sealed class Content(val type: ContentType)

data class HeaderContent(
    val title: String,
) : Content(ContentType.HEADER)

data class ImageContent(
    val imageResourceId: Int,
    val description: String,
) : Content(ContentType.IMAGE)

enum class ContentType(val code: Int) {
    HEADER(0), IMAGE(1)
}