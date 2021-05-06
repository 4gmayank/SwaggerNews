package com.space.swagger.networking

import retrofit2.http.GET

interface ArticleService {

    @GET("articles")
    suspend fun getArticles(): List<ArticleNetworkEntity>

}
