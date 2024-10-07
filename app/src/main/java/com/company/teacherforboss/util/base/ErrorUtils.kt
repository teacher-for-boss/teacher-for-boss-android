package com.company.teacherforboss.util.base

import android.util.Log
import com.company.teacherforboss.data.api.ApiClient
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject

fun <T> parseErrorResponse(response: retrofit2.Response<T>): String? {
    val errorBody = response.errorBody()?.string()
    if (errorBody != null && errorBody.isNotBlank()) {
        return try {
            val jsonObject = JSONObject(errorBody) // JSON으로 변환
            jsonObject.getString("message") // "message" 필드에서 에러 메시지 추출
        } catch (e: JSONException) {
            Log.e("ErrorResponse", "Failed to parse error body", e)
            null
        }
    }
    return null
}

object ErrorUtils {
//    fun getErrorResponse(errorbody: ResponseBody):ErrorUtils{
//        return ApiClient.client?.responseBodyConverter<ErrorUtils>(ErrorUtils::class.java,ErrorUtils::class.java.annotations)?.convert(errorbody)!!
//    }

    fun getErrorResponse(errorbody: ResponseBody):ErrorResponseBody{
        return ApiClient.client?.responseBodyConverter<ErrorResponseBody>(ErrorResponseBody::class.java,ErrorResponseBody::class.java.annotations)?.convert(errorbody)!!
    }

}