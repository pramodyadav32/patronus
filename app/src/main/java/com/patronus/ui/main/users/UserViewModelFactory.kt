package com.patronus.ui.main.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory


class UserViewModelFactory(private val repository: UserRepository) : Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModelFactory(repository) as T
    }
}