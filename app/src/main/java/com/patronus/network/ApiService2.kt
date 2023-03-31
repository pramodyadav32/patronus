package com.gaur.pixabayimagesearch.network

import com.gaur.pixabayimagesearch.network.model.PixabayResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService2 {

    @GET("api/")
    suspend fun getQueryImages(
        @Query("q") query:String,
        @Query("key") apiKey:String,
        @Query("image_type") imageType:String
    ):PixabayResponse


}