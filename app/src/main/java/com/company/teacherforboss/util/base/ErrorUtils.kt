package com.company.teacherforboss.util.base

import android.util.Log
import com.company.teacherforboss.data.api.ApiClient
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject

fun <T> parseErrorResponse(response: retrofit2.Response<T>): String? {
    val errorBody = response.errorBody()?.string()

    if (!errorBody.isNullOrBlank()) {
        return try {
            // 1. JSON 파싱 시도
            val jsonObject = JSONObject(errorBody)
            jsonObject.getString("message") // "message" 필드에서 에러 메시지 추출
        } catch (jsonException: JSONException) {
            Log.e("ErrorResponse", "Failed to parse JSON error body", jsonException)

            // 2. XML로 시작하는 경우 null 반환 (에러 메시지 무시)
            if (errorBody.startsWith("<")) {
                Log.i("ErrorResponse", "XML response detected, ignoring error message.")
                null // XML 응답 시 null 반환
            } else {
                // 3. 알 수 없는 형식일 경우 기본 메시지 반환
                null
            }
        }
    }
    return null // errorBody가 비어있거나 null일 경우 기본 메시지
}


object ErrorUtils {
//    fun getErrorResponse(errorbody: ResponseBody):ErrorUtils{
//        return ApiClient.client?.responseBodyConverter<ErrorUtils>(ErrorUtils::class.java,ErrorUtils::class.java.annotations)?.convert(errorbody)!!
//    }

    fun getErrorResponse(errorbody: ResponseBody):ErrorResponseBody{
        return ApiClient.client?.responseBodyConverter<ErrorResponseBody>(ErrorResponseBody::class.java,ErrorResponseBody::class.java.annotations)?.convert(errorbody)!!
    }

}