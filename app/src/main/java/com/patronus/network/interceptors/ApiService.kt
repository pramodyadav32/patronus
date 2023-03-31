package com.patronus.network.interceptors

import com.patronus.network.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by Pramod on 3/19/23.
 */
interface ApiService {

    //
    //    @POST("BlobStorage/blobs")
    //    Call<ApplicationResponse> getApplicationResponse(@Header("Content-Type") String content_type, @Body CategoryRequest verifyOTPRequest);

//    @GET("BlobStorage/blobs")
//    fun getAllFiles(@Header(Constant.CONTENT_TYPE) content_type: String?): Call<ArrayList<FileListResponse?>?>?

//    @GET("BlobStorage/StorageContainers")
//    fun getAllFolders(@Header("Content-Type") content_type: String?): Call<ArrayList<FolderListResponse?>?>?

    @GET("BlobStorage/StorageContainers")
    suspend fun getAllFolders(): ArrayList<FolderListData>

    @GET("BlobStorage/blobs")
    suspend fun getAllFiles(): ArrayList<FileListData>

    @GET("BlobStorage/blobs/{id}")
    suspend fun getFileById(@Path("id") id: String):FileListData

    @Multipart
    @POST("complaintApp/uploadPhotoWebApi")
    fun uploadProfile(
        @Part("CreationSource") CreationSource: RequestBody?,
        @Part("StorageContainerId") StorageContainerId: RequestBody?,
        @Part("CreatedBy") CreatedBy: RequestBody?,
        @Part file1: MultipartBody.Part
    ): Call<ImageUploadRaisecomplainResponse?>?

    @GET("BlobStorage/StorageContainers")
    fun imageSearch(@Header("Content-Type") content_type: String?): Call<ArrayList<FolderListResponse?>?>? //
    //    @POST("public_html/imageList")
    //    Call<ImageListResponse> imageList(@Header("Content-Type") String content_type, @Body ImageListRequest imageListRequest);
    //
    //    @POST("public_html/imageUpload")
    //    Call<ImageListResponse> imageUpload(@Header("Content-Type") String content_type, @Body ImageUploadRequest imageUploadRequest);
}