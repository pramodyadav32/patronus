package com.patronus.ui.main.userDetails

import com.patronus.network.interceptors.ApiFactory
import com.patronus.network.interceptors.ApiService
import com.patronus.network.model.UserDetailListData
import com.patronus.util.Resource
import javax.inject.Inject

/**
 * Created by Pramod on 3/31/23.
 */
class UserDetailsRepository @Inject constructor(private val apiService: ApiService?) {


    suspend fun getUserByID(fileId:String):Resource<UserDetailListData>{
        return  try{
            val _apiService = ApiFactory.application?.create(
                ApiService::class.java
            )
            val result = _apiService?.getFileById(fileId)
            Resource.Success(data = result)
        }catch (e: Exception){
            Resource.Error(message = e.message.toString())
        }
    }


}