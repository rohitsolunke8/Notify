package com.example.notify.di

import com.example.notify.api.NetworkInterceptor
import com.example.notify.api.NotesApi
import com.example.notify.api.UserApi
import com.example.notify.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
    }

    @Singleton
    @Provides
    fun provideOkhttpClient(authInterceptor: NetworkInterceptor): OkHttpClient {
        return try {
            OkHttpClient.Builder()
                .addNetworkInterceptor(authInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build()
        } catch (e: SocketTimeoutException){
            e.message
        } as OkHttpClient
    }

    @Singleton
    @Provides
    fun providesUserApi(retrofit: Retrofit.Builder): UserApi {
        return try {
            retrofit.build().create(UserApi::class.java)
        } catch (e: Exception) {
            e.message
        } as UserApi
    }

    @Singleton
    @Provides
    fun provideNotesApi(retrofit: Retrofit.Builder, okHttpClient: OkHttpClient): NotesApi {
        return try {
            retrofit
                .client(okHttpClient)
                .build().create(NotesApi::class.java)
        } catch (e: Exception) {
            e.message
        } as NotesApi
    }


//    @Singleton
//    @Provides
//    fun provideAuthRetrofit(okHttpClient: OkHttpClient): Retrofit {
//        return Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create())
//            .baseUrl(Constants.BASE_URL)
//            .client(okHttpClient)
//            .build()
//    }
}