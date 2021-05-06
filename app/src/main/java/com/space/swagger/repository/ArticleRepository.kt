package com.space.swagger.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.space.swagger.model.Article
import com.space.swagger.networking.ArticleService
import com.space.swagger.networking.NetworkMapper
import com.space.swagger.utils.DataState
import java.lang.Exception


class ArticleRepository constructor(
    private val articleDao: com.space.swagger.db.ArticleDao,
    private val articleService: ArticleService,
    private val cacheMapper: com.space.swagger.db.CacheMapper,
    private val networkMapper: NetworkMapper
){
    suspend fun getArticles(): Flow<DataState<List<Article>>> = flow {
        emit(DataState.Loading)

        try {
            val networkArticles = articleService.getArticles()
            val articles = networkMapper.mapFromEntityList(networkArticles)
            if(articles.isNotEmpty()) {
                articles.forEach {
                    articleDao.insert(cacheMapper.mapToEntity(it))
                }
            }
            val cachedArticles = articleDao.getArticles()
            emit(DataState.Success(cacheMapper.mapFromEntityList(cachedArticles)))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }

    }

}
