package com.example.teacherforboss.data.intercepter

import android.content.Context
import com.example.teacherforboss.data.tokenmanager.TokenManager
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    @ApplicationContext val context: Context,
    val tokenManager:TokenManager
):Interceptor{
    val CODE_ERROR=401 //TODO
    override fun intercept(chain: Interceptor.Chain): Response {
        val token=tokenManager.getAccessToken(context)
        //header Authorizatioin 부분에 access token 추가
        val request=chain.request().newBuilder().header(AUTHORIZATION,"Bearer ${token}").build()

        val response=chain.proceed(request)//header 추가한 request의 반환값

        //TODO: 만약에 authenticator가 제대로 돌아가지 않는다면 여기서 access token 갱신 로직 구현하기
//        if(response.code==401){
//        }
        //서버로부터 새로운 access token 발급받았을때
//        if(response.code==HTTP_OK){
//            val newAccessToken:String=response.header(AUTHORIZATION,null)?:return response
//            //Authorization 없을시-> 토큰 재발급 아님, response 리턴
//
//            CoroutineScope(Dispatchers.IO).launch {
//                //val existedAccessToken=tokenManager.getAccessToken().first()
//                val existedAccessToken=tokenManager.getAccessToken(context)
//                if(existedAccessToken!=newAccessToken){
//                    tokenManager.saveAccessToken(context,newAccessToken)
//                    Log.d("access-token","new access token=${newAccessToken}, existed:${existedAccessToken}")
//
//                    Log.d("access-token","new access token=${response.request}, ${response.message}")
//                }
//                else{
//                    Log.d("access-token","new access token=${response.request}, request:${response.request}, message:${response.message}")
//                }
//
//            }
//        }
        return response
    }

    private fun errorResponse(request:Request):Response=Response.Builder()
        .request(request)
        .protocol(Protocol.HTTP_2)
        .code(CODE_ERROR)
        .body(ResponseBody.create(null,""))
        .build()

    companion object{
        private val AUTHORIZATION="Authorization"

    }

}