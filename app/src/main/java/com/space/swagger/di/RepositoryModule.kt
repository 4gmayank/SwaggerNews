package com.space.swagger.di

import com.space.swagger.networking.ArticleService
import com.space.swagger.networking.NetworkMapper
import com.space.swagger.repository.ArticleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideArticleRepository(
        articleDao: com.space.swagger.db.ArticleDao,
        service: ArticleService,
        cacheMapper: com.space.swagger.db.CacheMapper,
        networkMapper: NetworkMapper
    ): ArticleRepository {
        return ArticleRepository(articleDao, service, cacheMapper, networkMapper)
    }

}