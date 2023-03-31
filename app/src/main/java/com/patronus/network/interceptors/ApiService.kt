package com.patronus.network.interceptors

import com.patronus.network.model.*
import retrofit2.http.*

/**
 * Created by Pramod on 3/31/23.
 */
interface ApiService {

    @GET("users")
    suspend fun getAllFolders(): UserListResponse

    @GET("users/{id}")
    suspend fun getFileById(@Path("id") id: String):UserDetailListData

}