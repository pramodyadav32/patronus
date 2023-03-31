package com.patronus.ui.main.files

import android.util.Log
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
class FilesViewModel
@Inject constructor(
    private val filesRepository: FilesRepository
) : ViewModel() {

    val list: MutableLiveData<FileState> = MutableLiveData(FileState())

    init {
        getFileList()
    }

    fun getFileList() = viewModelScope.launch {
        list.value = FileState(isLoading = true)
        try {
            val result = filesRepository.getAllFiles()
            when (result) {
                is Resource.Error -> {
                    list.value = FileState(error = "Something went wrong")
                }
                is Resource.Success -> {
                    result.data?.let {
                        list.value = FileState(data = it)
                    }


                }
                is Resource.Loading -> {

                }
            }
        } catch (e: Exception) {
            list.value = FileState(error = "Something went wrong")
        }

    }



    fun uploadFile() = viewModelScope.launch {
        list.value = FileState(isLoading = true)
        try {
            val result = filesRepository.getAllFiles()
            when (result) {
                is Resource.Error -> {
                    list.value = FileState(error = "Something went wrong")
                }
                is Resource.Success -> {
                    Log.d("aaaaaaaaaaaaaaaaa2222", "aaaaaaaaaaaaa file init" + result.data)
                    result.data?.let {
                        list.value = FileState(data = it)
                    }


                }
                is Resource.Loading -> {

                }
            }
        } catch (e: Exception) {
            list.value = FileState(error = "Something went wrong")
        }

    }


}