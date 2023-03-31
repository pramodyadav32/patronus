package com.patronus.ui.main.users

import com.patronus.network.interceptors.ApiFactory
import com.patronus.network.interceptors.ApiService
import com.patronus.network.model.UserListResponse
import com.patronus.util.Resource
import java.lang.Exception
import javax.inject.Inject

/**
 * Created by Pramod on 3/31/23.
 */
class UserRepository @Inject constructor(private val apiService: ApiService?) {


    suspend fun getAllUsers():Resource<UserListResponse>{
      return  try{
          val _apiService = ApiFactory.application?.create(
              ApiService::class.java
          )
            val result = _apiService?.getAllFolders()
            Resource.Success(data = result)
        }catch (e:Exception){
            Resource.Error(message = e.message.toString())
        }
    }
}