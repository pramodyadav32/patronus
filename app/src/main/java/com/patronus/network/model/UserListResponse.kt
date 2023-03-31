package com.patronus.network.model

import androidx.annotation.Keep

@Keep
data class UserListResponse(
    val customers: ArrayList<UserListData>
)