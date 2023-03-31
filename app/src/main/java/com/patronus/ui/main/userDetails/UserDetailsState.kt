package com.patronus.ui.main.userDetails

import com.patronus.network.model.UserDetailListData


/**
 * Created by Pramod on 3/31/23.
 */
data class UserDetailsState(
    val isLoading:Boolean=false,
    val data: UserDetailListData = UserDetailListData(),
    val error:String=""
)
