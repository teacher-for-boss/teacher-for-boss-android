package com.example.teacherforboss.util.base

import com.example.teacherforboss.data.api.ApiClient
import okhttp3.ResponseBody

object ErrorUtils {
//    fun getErrorResponse(errorbody: ResponseBody):ErrorUtils{
//        return ApiClient.client?.responseBodyConverter<ErrorUtils>(ErrorUtils::class.java,ErrorUtils::class.java.annotations)?.convert(errorbody)!!
//    }

    fun getErrorResponse(errorbody: ResponseBody):ErrorResponseBody{
        return ApiClient.client?.responseBodyConverter<ErrorResponseBody>(ErrorResponseBody::class.java,ErrorResponseBody::class.java.annotations)?.convert(errorbody)!!
    }
}