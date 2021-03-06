package com.space.swagger.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class ArticleCacheEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "article_url")
    val articleUrl: String,

    @ColumnInfo(name = "image_url")
    val imageUrl: String,


    @ColumnInfo(name = "news_site")
    val newsSite: String,

    @ColumnInfo(name = "summary")
    val summary: String,

    @ColumnInfo(name = "published_at")
    val publishedAt: String
)
