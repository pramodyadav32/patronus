package com.patronus.network.model

import androidx.annotation.Keep
import com.gaur.pixabayimagesearch.network.model.Hit
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
data class FolderListResponse(
    val folderList: List<FolderListData>,
)