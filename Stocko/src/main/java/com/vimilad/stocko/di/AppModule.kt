package com.vimilad.stocko.di

import android.app.Application
import androidx.room.Room
import com.vimilad.stocko.data.local.StockoDatabase
import com.vimilad.stocko.data.remote.StockoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStockoApi(): StockoApi {
        return Retrofit.Builder()
            .baseUrl(StockoApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideStockoDatabase(app: Application): StockoDatabase {
        return Room.databaseBuilder(
            app,
            StockoDatabase::class.java,
            "stocko.db"
        ).build()
    }
}