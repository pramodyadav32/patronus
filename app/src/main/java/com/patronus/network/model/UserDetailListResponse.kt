package com.patronus.network.model

import androidx.annotation.Keep

@Keep
data class UserDetailListResponse(
    val userDetailList: List<UserDetailListData>,
)