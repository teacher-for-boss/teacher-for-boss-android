package com.example.teacherforboss.login

import android.content.Context
import android.media.session.MediaSession.Token
import android.util.Log
import com.example.teacherforboss.GlobalApplication
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

//access token 만료시 서버로부터 401 코드 전달받음->401 전달받을시 AuthAuthenticator 호출
//access token을 refresh token으로 교채하여 api 재요청
class AuthAuthenticator @Inject constructor(
    private val tokenManager:TokenManager,
    @ApplicationContext val context: Context
):Authenticator{
    val Authorization="Authorization"
    override fun authenticate(route: Route?, response: Response): Request? {

        val refreshToken=tokenManager.getRefreshToken(context)

        // ver2. datastore
//            val refreshToken= runBlocking {
//                tokenManager.getRefreshToken().first()
//            }
        if(refreshToken==null){
            Log.e("token","refresh token is empty")
                response.close()
                return null
        }


        val newAccessToken=reissueToken(refreshToken)
        Log.d("token","access_token 재발급 완료:${newAccessToken}")
        return replaceToken(newAccessToken,response.request)
        //401에러로 받은 response에 대한 request


//          return newRequestWithToken(refreshToken,response.request)

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
        val userRepo=UserRepository()
        val response=userRepo.loginReissue(token)
        if(response?.code()==200){
            val newToken=response.body()?.result?.accessToken ?:""
            if(!newToken.isBlank()) return newToken
            throw IllegalArgumentException("새로 발급 받은 토큰이 비어있음!")
        }
        throw IllegalArgumentException("토큰 재발급 실패")

    }

}