package com.example.teacherforboss.data.intercepter

import android.content.Context
import com.example.teacherforboss.data.tokenmanager.TokenManager
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    @ApplicationContext val context: Context,
    val tokenManager:TokenManager
):Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        var token=tokenManager.getAccessToken(context)

        //header Authorizatioin 부분에 access token 추가
        val request=chain.request().newBuilder().header(AUTHORIZATION,"Bearer ${token}").build()

        return try{
            chain.proceed(request)

        }catch (e:IOException){
            errorResponse(request)
        }

    }

    private fun errorResponse(request:Request):Response=Response.Builder()
        .request(request)
        .protocol(Protocol.HTTP_2)
        .code(CODE_ERROR)
        .body(ResponseBody.create(null,""))
        .build()

    companion object{
        private val AUTHORIZATION="Authorization"
        val CODE_ERROR=401

    }

}