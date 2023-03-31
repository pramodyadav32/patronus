package com.patronus.network.interceptors

/**
 * Created by Pramod on 3/19/23.
 */
class UnexpectedAPIError @JvmOverloads constructor(
    responseCode: Int,
    message: String?,
    code: String? = null,
    description: String? = null
) : YelpFusionError(responseCode, message!!, code!!, description!!)