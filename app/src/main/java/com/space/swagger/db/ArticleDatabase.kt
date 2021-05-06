package com.space.swagger.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [com.space.swagger.db.ArticleCacheEntity::class], version = 2)
abstract class ArticleDatabase: RoomDatabase(){

    abstract fun articleDao(): com.space.swagger.db.ArticleDao

    companion object {
        const val DATABASE_NAME = "articles_db"
    }
}