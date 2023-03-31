package com.patronus.network.model

import androidx.annotation.Keep

@Keep
data class FileListResponse(
    val folderList: List<FileListData>,
)