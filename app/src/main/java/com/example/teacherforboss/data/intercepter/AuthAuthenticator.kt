package com.example.teacherforboss.data.intercepter

import android.content.Context
import android.util.Log
import com.example.teacherforboss.data.repository.UserRepositoryImpl
import com.example.teacherforboss.data.tokenmanager.TokenManager
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

//access token 만료시 서버로부터 401 코드 전달받음->401 전달받을시 AuthAuthenticator 호출
//access token을 refresh token으로 교채하여 api 재요청
class AuthAuthenticator @Inject constructor(
    @ApplicationContext val context: Context,
    private val tokenManager: TokenManager,
    ):Authenticator{
    val Authorization="Authorization"
    override fun authenticate(route: Route?, response: Response): Request? {

        val refreshToken= tokenManager.getRefreshToken(context)
        if(refreshToken==null){
            Log.e("token","refresh token is empty")
                response.close()
                return null
        }


        val newAccessToken=reissueToken(refreshToken)
        Log.d("token","access_token 재발급 완료:${newAccessToken}")
        TokenManager.saveAccessToken(context, newAccessToken)
        return replaceToken(newAccessToken,response.request)
        //401에러로 받은 response에 대한 request (access token 만료 code명:AUTH4007)

    }

    private fun replaceToken(newToken:String,request:Request):Request=
        request.newBuilder()
            .removeHeader(Authorization).apply {
                addHeader(Authorization,newToken)
            }.build()

//    private fun newRequestWithToken(token:String,request:Request):Request=
//        request.newBuilder()
//            .header("Authorization",token)
//            .build()
    fun reissueToken(token:String):String{
        val userRepo= UserRepositoryImpl()
        val response=userRepo.loginReissue(token)
        if(response?.body()?.code=="COMMON200"){
            val newToken=response.body()?.result?.accessToken ?:""
            if(!newToken.isBlank()) return newToken
            throw IllegalArgumentException("새로 발급 받은 토큰이 비어있음!")
        }
        throw IllegalArgumentException("토큰 재발급 실패")

    }

}