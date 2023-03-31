package com.patronus.network.model

import androidx.annotation.Keep

@Keep
data class UserListData(
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val gender: String = "",
    val phoneNumber: String = "",
    val imageUrl: String = "",
    val stickers: Array<String> = arrayOf<String>()
)