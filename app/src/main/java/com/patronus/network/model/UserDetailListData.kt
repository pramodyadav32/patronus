package com.patronus.network.model

import androidx.annotation.Keep

@Keep
data class UserDetailListData(
    val id: String = "",
    val imageUrl: String = "",
    val currentLatitude: String? = "",
    val currentLongitude: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val stickers: Array<String> = arrayOf<String>(),
    val gender: String = "",
    val phoneNumber: String = "",
    val address: Address = Address()
)

data class Address(
    val street: String = "",
    val city: String = "",
    val state: String = "",
    val zip: String = "",
    val country: String = "",
)