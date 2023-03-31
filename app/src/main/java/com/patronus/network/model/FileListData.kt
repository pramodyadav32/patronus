package com.patronus.network.model

import androidx.annotation.Keep

@Keep
data class FileListData(
    val id: String = "",
    val fileName: String = "",
    val filesize: String? = "",
    val contentType: String = "",
    val createdBy: String = "",
    val downloadUrl: String = "",
    val previewUrl: String = "",
    val createdTimestamp: String = "",
    val creationSource: String = "",
    val description: String = "",
    val domainIdentityType: String = "",
    val domainIdentityValue: String = ""
)