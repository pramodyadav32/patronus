package com.patronus.ui.main.userDetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patronus.util.Constant
import com.patronus.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Pramod on 3/31/23.
 */
@HiltViewModel
class UserDetailsViewModel
@Inject constructor(
    private val filesRepository: UserDetailsRepository
) : ViewModel() {

    val data: MutableLiveData<UserDetailsState> = MutableLiveData(UserDetailsState())

    init {
        getFileByID(Constant.DEVICE_HOLDER_ID)
    }

    fun getFileByID(fileId: String) = viewModelScope.launch {
        data.value = UserDetailsState(isLoading = true)
        try {
            val result = filesRepository.getUserByID(fileId)
            when (result) {
                is Resource.Error -> {
                    data.value = UserDetailsState(error = "Something went wrong")
                }
                is Resource.Success -> {
                    result.data?.let {
                        data.value = UserDetailsState(data = it)
                    }


                }
                is Resource.Loading -> {

                }
            }
        } catch (e: Exception) {
            data.value = UserDetailsState(error = "Something went wrong")
        }

    }


}