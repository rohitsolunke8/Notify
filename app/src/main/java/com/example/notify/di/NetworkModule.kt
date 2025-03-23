package com.example.notify.di

import android.content.Context
import androidx.datastore.core.MultiProcessDataStoreFactory
import com.example.notify.api.NetworkInterceptor
import com.example.notify.api.NotesApi
import com.example.notify.api.UserApi
import com.example.notify.utils.Constants
import com.example.notify.utils.NotifyPreferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
        return OkHttpClient.Builder()
            .addNetworkInterceptor(authInterceptor).build()
    }

    @Singleton
    @Provides
    fun providesUserApi(retrofit: Retrofit.Builder): UserApi {
        return retrofit.build().create(UserApi::class.java)
    }

    @Singleton
    @Provides
    fun provideNotesApi(retrofit: Retrofit.Builder, okHttpClient: OkHttpClient): NotesApi {
        return retrofit
            .client(okHttpClient)
            .build().create(NotesApi::class.java)
    }

//    @Singleton
//    @Provides
//    fun provideDataPreferenceStore(@ApplicationContext context: Context) : NotifyPreferencesDataStore =
//        MultiProcessDataStoreFactory.create(provideDataPreferenceStore(context))




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