package com.patronus.ui.main.files

import com.patronus.network.model.FileListData
import com.patronus.network.model.FolderListData
import com.patronus.network.model.FolderListResponse


/**
 * Created by Pramod on 3/19/23.
 */
data class FileState(
    val isLoading:Boolean=false,
    val data: ArrayList<FileListData>? = ArrayList<FileListData>(),
    val error:String=""
)
