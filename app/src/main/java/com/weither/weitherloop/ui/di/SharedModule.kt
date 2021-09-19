package com.weither.weitherloop.ui.di

import android.content.Context
import com.weither.weitherloop.data.NetworkUtils
import com.weither.weitherloop.data.datasource.CityRemoteDataSource
import com.weither.weitherloop.data.network.CityService
import com.weither.weitherloop.data.network.CityService.Companion.baseUrl
import com.weither.weitherloop.data.repository.CityRepositoryImpl
import com.weither.weitherloop.domain.ApplicationScope
import com.weither.weitherloop.domain.IoDispatcher
import com.weither.weitherloop.domain.repository.CityRepository
import com.weither.weitherloop.utils.NetworkUtilsImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class SharedModule {

    @Singleton
    @Provides
    fun provideNetworkUtils(
        @ApplicationContext context: Context
    ): NetworkUtils {
        return NetworkUtilsImpl(context)
    }

    @Singleton
    @Provides
    fun provideCityRepository(
        cityDataSource: CityRemoteDataSource,
        networkUtils: NetworkUtils
    ): CityRepository {
        return CityRepositoryImpl(
            cityDataSource,
            networkUtils
        )
    }

    @ApplicationScope
    @Singleton
    @Provides
    fun providesApplicationScope(
        @IoDispatcher defaultDispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + defaultDispatcher)

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesAlbumApi(retrofit: Retrofit): CityService = retrofit.create(
        CityService::class.java)


}