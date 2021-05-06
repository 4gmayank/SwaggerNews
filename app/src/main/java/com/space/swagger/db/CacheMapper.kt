package com.space.swagger.db

import com.space.swagger.model.Article
import com.space.swagger.utils.EntityMapper
import javax.inject.Inject

class CacheMapper @Inject constructor() : EntityMapper<com.space.swagger.db.ArticleCacheEntity, Article> {
    override fun mapFromEntity(entity: com.space.swagger.db.ArticleCacheEntity): Article {
        return Article(
            id = entity.id,
            title = entity.title,
            articleUrl = entity.articleUrl,
            imageUrl = entity.imageUrl,
            newsSite = entity.newsSite,
            summary = entity.summary,
            publishedAt = entity.publishedAt
        )
    }

    override fun mapToEntity(domainModel: Article): com.space.swagger.db.ArticleCacheEntity {
        return com.space.swagger.db.ArticleCacheEntity(
            id = domainModel.id,
            title = domainModel.title,
            articleUrl = domainModel.articleUrl,
            imageUrl = domainModel.imageUrl,
            newsSite = domainModel.newsSite,
            summary = domainModel.summary,
            publishedAt = domainModel.publishedAt
        )
    }

    fun mapFromEntityList(entities: List<com.space.swagger.db.ArticleCacheEntity>): List<Article> {
        return entities.map { mapFromEntity(it) }
    }
}
