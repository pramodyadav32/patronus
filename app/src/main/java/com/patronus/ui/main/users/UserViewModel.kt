package com.patronus.ui.main.users

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patronus.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Pramod on 3/31/23.
 */
@HiltViewModel
class UserViewModel
@Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val list: MutableLiveData<UserState> = MutableLiveData(UserState())

    init {
        getFolderList()
    }

    fun getFolderList() = viewModelScope.launch {
        list.value = UserState(isLoading = true)
        try {
            val result = userRepository.getAllUsers()
            when (result) {
                is Resource.Error -> {
                    list.value = UserState(error = "Something went wrong")
                }
                is Resource.Success -> {
                    result.data?.customers.let {
                        list.value = UserState(data = it)
                    }


                }
                is Resource.Loading -> {

                }
            }
        } catch (e: Exception) {
            list.value = UserState(error = "Something went wrong")
        }

    }


}