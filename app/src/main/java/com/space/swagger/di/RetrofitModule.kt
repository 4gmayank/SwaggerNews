package com.space.swagger.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.space.swagger.networking.ArticleService
import com.space.swagger.utils.Constants
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideGson() : Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(gson: Gson) : Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(Constants.swagerUiUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideArticleService(retrofitBuilder: Retrofit.Builder): ArticleService {
        return retrofitBuilder
            .build()
            .create(ArticleService::class.java)
    }
}
