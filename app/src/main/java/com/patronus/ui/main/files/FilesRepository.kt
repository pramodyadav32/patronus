package com.patronus.ui.main.files

import com.patronus.network.interceptors.ApiFactory
import com.patronus.network.interceptors.ApiService
import com.patronus.network.model.FileListData
import com.patronus.network.model.FolderListResponse
import com.patronus.util.Resource
import java.lang.Exception
import javax.inject.Inject

/**
 * Created by Pramod on 3/19/23.
 */
class FilesRepository @Inject constructor(private val apiService: ApiService?) {


    suspend fun getAllFiles():Resource<ArrayList<FileListData>>{
        return  try{
            val _apiService = ApiFactory.application?.create(
                ApiService::class.java
            )
            val result = _apiService?.getAllFiles()
            Resource.Success(data = result)
        }catch (e: Exception){
            Resource.Error(message = e.message.toString())
        }
    }


}