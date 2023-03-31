package com.patronus.ui.main.fileDetails

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patronus.ui.main.files.FileState
import com.patronus.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Pramod on 3/19/23.
 */
@HiltViewModel
class FileDetailsViewModel
@Inject constructor(
    private val filesRepository: FileDetailsRepository
) : ViewModel() {

    val data: MutableLiveData<FileDetailsState> = MutableLiveData(FileDetailsState())


    fun getFileByID(fileId: String) = viewModelScope.launch {
        data.value = FileDetailsState(isLoading = true)
        try {
            val result = filesRepository.getFileByID(fileId)
            when (result) {
                is Resource.Error -> {
                    data.value = FileDetailsState(error = "Something went wrong")
                }
                is Resource.Success -> {
                    Log.d("aaaaaaaaaaaaaaaaa2222", "aaaaaaaaaaaaa file detail init" + result.data)
                    result.data?.let {
                        data.value = FileDetailsState(data = it)
                    }


                }
                is Resource.Loading -> {

                }
            }
        } catch (e: Exception) {
            data.value = FileDetailsState(error = "Something went wrong")
        }

    }


}