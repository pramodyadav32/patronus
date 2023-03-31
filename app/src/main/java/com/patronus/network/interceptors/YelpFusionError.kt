package com.patronus.network.interceptors

import java.io.IOException

/**
 * Created by Pramod on 3/31/23.
 */
abstract class YelpFusionError(
    val responseCode: Int,
    override val message: String,
    val code: String,
    val description: String
) : IOException()