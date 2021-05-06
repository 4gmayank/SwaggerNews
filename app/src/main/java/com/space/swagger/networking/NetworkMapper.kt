package com.space.swagger.networking

import com.space.swagger.model.Article
import com.space.swagger.utils.EntityMapper
import javax.inject.Inject

class NetworkMapper
@Inject
constructor() : EntityMapper<ArticleNetworkEntity, Article> {

    override fun mapFromEntity(entity: ArticleNetworkEntity): Article {
        return Article(
            id = entity.id,
            title = entity.title,
            articleUrl = entity.url,
            imageUrl = entity.imageUrl,
            newsSite = entity.newsSite,
            summary = entity.summary,
            publishedAt = entity.publishedAt
        )
    }

    override fun mapToEntity(domainModel: Article): ArticleNetworkEntity {
        return ArticleNetworkEntity(
            id = domainModel.id,
            title = domainModel.title,
            url = domainModel.articleUrl,
            imageUrl = domainModel.imageUrl,
            newsSite = domainModel.newsSite,
            summary = domainModel.summary,
            publishedAt = domainModel.publishedAt
        )
    }

    fun mapFromEntityList(entities: List<ArticleNetworkEntity>): List<Article> {
        return entities.map { mapFromEntity(it) }
    }

}
