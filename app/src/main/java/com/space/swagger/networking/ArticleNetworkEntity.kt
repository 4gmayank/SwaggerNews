package com.space.swagger.networking

data class ArticleNetworkEntity(
    val id: String,
    val title: String,
    val url: String,
    val imageUrl: String,
    val newsSite: String,
    val summary: String,
    val publishedAt: String
)
