package com.example.teacherforboss.data.intercepter

import android.content.Context
import android.util.Log
import com.example.teacherforboss.data.repository.UserRepositoryImpl
import com.example.teacherforboss.data.tokenmanager.TokenManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
    @ApplicationContext val context: Context,
    private val tokenManager: TokenManager,
    ):Authenticator{
    override fun authenticate(route: Route?, response: Response): Request? {

        tokenManager.clearAccessToken(context) //만료된 액세스 토큰 삭제
        val refreshToken= tokenManager.getRefreshToken(context)

        if(refreshToken==null){
            Log.e("token","refresh token이 존재하지 않습니다")
                response.close()
                return null
        }

        var newAccessToken:String=""
        CoroutineScope(Dispatchers.IO).launch {
            try {
                newAccessToken=reissueToken(refreshToken)
                if (newAccessToken.isNullOrEmpty()){
                    Log.e("token","액세스 토큰이 비어있습니다")
                }else{
                    tokenManager.saveAccessToken(context,newAccessToken)
                }

            }catch (e:Exception){
                Log.e("token", "토큰 재발급 중 예외 발생", e)
                null
            }

        }

        return replaceToken(newAccessToken,response)

//        val newAccessToken= runBlocking {
//            try {
//                reissueToken(refreshToken)
//            }catch (e:Exception){
//                Log.e("token", "토큰 재발급 중 예외 발생", e)
//                null
//            }
//        }
//
//        if(newAccessToken==null){
//            Log.e("token","액세스 토큰이 비어있습니다")
//            return null
//        }
//        tokenManager.saveAccessToken(context,newAccessToken)
//
//        return replaceToken(newAccessToken,response)
    }

    private fun replaceToken(newToken:String,response: Response):Request=
        response.request.newBuilder()
            .removeHeader(AUTHORIZATION).apply {
                addHeader(AUTHORIZATION,"Bearer ${newToken}")
            }.build()

    suspend fun reissueToken(token:String):String{
        val userRepo = UserRepositoryImpl()
        val response = userRepo.loginReissue(token)

        if (response!!.isSuccessful) {
            val loginResponse = response.body()
            if (loginResponse != null && loginResponse.code == "COMMON200") {
                val newToken = loginResponse.result.accessToken // 새로운 액세스 토큰
                if (!newToken.isBlank()) {
                    return newToken // 새로운 토큰 반환
                } else {
                    Log.e("token", "재발급된 토큰이 비어있음")
                    throw IllegalArgumentException("재발급된 토큰이 비어있음")
                }
            }
        }
        Log.e("token", "토큰 재발급 실패")
        throw IllegalArgumentException("토큰 재발급 실패")
        }

    companion object{
        const val AUTHORIZATION="Authorization"
        }
    }