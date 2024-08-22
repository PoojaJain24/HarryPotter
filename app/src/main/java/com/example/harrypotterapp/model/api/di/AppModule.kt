package com.example.harrypotterapp.model.api.di

import android.content.Context
import androidx.room.Room
import com.example.harrypotterapp.model.api.ApiService
import com.example.harrypotterapp.model.api.ApiService.Companion.BASE_URL
import com.example.harrypotterapp.model.database.HarryPotterAppDatabase
import com.example.harrypotterapp.model.database.HarryPotterDao/*
import com.example.harrypotterapp.model.datasource.HarryPotterDataSource
import com.example.harrypotterapp.model.datasource.LocalHarryPotterDataSource
import com.example.harrypotterapp.model.datasource.RemoteHarryPotterDataSource*/
import com.example.harrypotterapp.model.repository.HarryPotterRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        val httpClient = OkHttpClient().newBuilder().apply {
            addInterceptor(httpLoggingInterceptor)
        }

        httpClient.apply { readTimeout(15, TimeUnit.SECONDS) }

        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideHarryPotterAppDatabase(@ApplicationContext appContext: Context): HarryPotterAppDatabase {
        return Room.databaseBuilder(
            appContext, HarryPotterAppDatabase::class.java,
            "harryPotterCharacters_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideHarryPotterCharacterDao(database: HarryPotterAppDatabase): HarryPotterDao {
        return database.harryPotterDao()
    }

    /* @Provides
     @Singleton
     fun provideRemoteHarryPotterDataSource(apiService: ApiService):RemoteHarryPotterDataSource{
         return RemoteHarryPotterDataSource(apiService)
     }

     @Provides
     @Singleton
     fun provideLocalHarryPotterDataSource(dao: HarryPotterDao): LocalHarryPotterDataSource {
         return LocalHarryPotterDataSource(dao)
     }*/

    @Provides
    @Singleton
    fun provideHarryPotterRepository(
        apiService: ApiService,
        dao: HarryPotterDao
    ): HarryPotterRepository {
        return HarryPotterRepository(apiService, dao)
    }


}