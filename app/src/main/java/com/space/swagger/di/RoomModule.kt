package com.space.swagger.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Singleton
    @Provides
    fun provideArticleDb(@ApplicationContext context: Context): com.space.swagger.db.ArticleDatabase {
        return Room
            .databaseBuilder(
                context,
                com.space.swagger.db.ArticleDatabase::class.java,
                com.space.swagger.db.ArticleDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideArticleDAO(database: com.space.swagger.db.ArticleDatabase): com.space.swagger.db.ArticleDao {
        return database.articleDao()
    }

}