package com.patronus.ui.main.folders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory


class FolderViewModelFactory(private val repository: FolderRepository) : Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FolderViewModelFactory(repository) as T
    }
}