package com.patronus.ui.main.folders

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patronus.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Pramod on 3/19/23.
 */
@HiltViewModel
class FolderViewModel
@Inject constructor(
    private val folderRepository: FolderRepository
) : ViewModel() {

    val list: MutableLiveData<FolderState> = MutableLiveData(FolderState())

    init {
        getFolderList()
    }

    fun getFolderList() = viewModelScope.launch {
        list.value = FolderState(isLoading = true)
        try {
            val result = folderRepository.getAllFolders()
            when (result) {
                is Resource.Error -> {
                    list.value = FolderState(error = "Something went wrong")
                }
                is Resource.Success -> {
                    result.data?.let {
                        list.value = FolderState(data = it)
                    }


                }
                is Resource.Loading -> {

                }
            }
        } catch (e: Exception) {
            list.value = FolderState(error = "Something went wrong")
        }

    }


}