package com.geekydroid.androidbookcompose

import retrofit2.Response
import retrofit2.http.GET

interface Apiservice {

    @GET("/albums")
    suspend fun getAlbums():Response<List<Album>>
}