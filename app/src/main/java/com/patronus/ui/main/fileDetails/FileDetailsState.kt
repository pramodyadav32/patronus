package com.patronus.ui.main.fileDetails

import com.patronus.network.model.FileListData


/**
 * Created by Pramod on 3/19/23.
 */
data class FileDetailsState(
    val isLoading:Boolean=false,
    val data: FileListData? = FileListData(),
    val error:String=""
)
