package com.patronus.network.model

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
data class FolderListData(
    val id: String = "",
    val name: String = "",
    val storageType: String? = "",
    val maxFileSize: String = ""
)