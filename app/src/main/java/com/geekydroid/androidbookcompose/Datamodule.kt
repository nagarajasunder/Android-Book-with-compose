package com.geekydroid.androidbookcompose

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object Datamodule {

    @Singleton
    @Provides
    fun provideRetrofit():Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.5")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit) : Apiservice = retrofit.create(Apiservice::class.java)
}