package com.patronus.network.interceptors

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ErrorHandlingInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response: Response = chain.proceed(chain.request())
        if (!response.isSuccessful) {
            throw parseError(
                response.code,
                response.message,
                if (response.body != null) response.body!!.string() else null
            )
        }
        return response
    }

    @Throws(IOException::class)
    private fun parseError(code: Int, message: String, responseBody: String?): YelpFusionError {
        if (responseBody == null) {
            return UnexpectedAPIError(code, message)
        }
        val errorJsonNode: JsonNode = objectMapper.readTree(responseBody).path("error")
        val errorCode: String = errorJsonNode.path("code").asText()
        val errorText: String = errorJsonNode.path("description").asText()
        return UnexpectedAPIError(code, message, errorCode, errorText)
    }

    companion object {
        private val objectMapper: ObjectMapper = ObjectMapper()
    }
}