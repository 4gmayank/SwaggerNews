package com.space.swagger.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(articleCacheEntity: com.space.swagger.db.ArticleCacheEntity): Long

    @Query("SELECT * FROM articles")
    suspend fun getArticles(): List<com.space.swagger.db.ArticleCacheEntity>

}