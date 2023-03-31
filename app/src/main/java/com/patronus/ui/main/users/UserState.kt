package com.patronus.ui.main.users

import com.patronus.network.model.UserListData


/**
 * Created by Pramod on 3/31/23.
 */
data class UserState(
    val isLoading:Boolean=false,
    val data: ArrayList<UserListData>? = ArrayList<UserListData>(),
    val error:String=""
)
