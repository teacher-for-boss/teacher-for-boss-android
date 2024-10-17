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

            try {
                // 2. JSON 파싱 실패 시 XML 파싱 시도
                if (errorBody.startsWith("<")) {
                    // XML 시작 태그 여부로 확인
                    val xmlMessage = extractMessageFromXml(errorBody)
                    xmlMessage ?: "Unknown XML error"
                } else {
                    "Unknown error format"
                }
            } catch (xmlException: Exception) {
                Log.e("ErrorResponse", "Failed to parse XML error body", xmlException)
                "Failed to parse error response"
            }
        }
    }
    return "Unknown error"
}

// XML에서 에러 메시지를 추출하는 함수
fun extractMessageFromXml(xml: String): String? {
    return try {
        val factory = javax.xml.parsers.DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()
        val inputStream = xml.byteInputStream()
        val document = builder.parse(inputStream)
        val messageNode = document.getElementsByTagName("message").item(0)
        messageNode?.textContent
    } catch (e: Exception) {
        Log.e("ErrorResponse", "XML parsing error", e)
        null
    }
}


object ErrorUtils {
//    fun getErrorResponse(errorbody: ResponseBody):ErrorUtils{
//        return ApiClient.client?.responseBodyConverter<ErrorUtils>(ErrorUtils::class.java,ErrorUtils::class.java.annotations)?.convert(errorbody)!!
//    }

    fun getErrorResponse(errorbody: ResponseBody):ErrorResponseBody{
        return ApiClient.client?.responseBodyConverter<ErrorResponseBody>(ErrorResponseBody::class.java,ErrorResponseBody::class.java.annotations)?.convert(errorbody)!!
    }

}