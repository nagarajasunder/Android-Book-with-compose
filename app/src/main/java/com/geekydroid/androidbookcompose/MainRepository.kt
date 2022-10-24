package com.geekydroid.androidbookcompose

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(private val service:Apiservice) {

    suspend fun getAllAlbums() = service.getAlbums()
}