package com.example.teacherforboss.login

import com.example.teacherforboss.signup.EmailCheckRequest
import com.example.teacherforboss.signup.EmailCheckResponse
import com.example.teacherforboss.signup.EmailRequest
import com.example.teacherforboss.signup.EmailResponse
import com.example.teacherforboss.signup.SignupRequest
import com.example.teacherforboss.signup.SignupResponse
import com.kakao.sdk.user.model.User
import retrofit2.Response
import retrofit2.http.Header

class UserRepository {
    suspend fun loginUser(loginRequest: LoginRequest):
            Response<LoginResponse>?{
        return UserApi.getApi()?.loginUser(loginRequest=loginRequest)
    }
    fun loginReissue(refreshToken:String):
            Response<LoginResponse>?{
        return UserApi.getApi()?.loginReissue(refreshToken ="Bearer ${refreshToken}" )
    }

    suspend fun emailUser(emailRequest: EmailRequest):
            Response<EmailResponse>?{
        return UserApi.getApi()?.emailUser(emailRequest = emailRequest)
    }

    suspend fun emailCheck(emailCheckRequest: EmailCheckRequest):
            Response<EmailCheckResponse>?{
        return UserApi.getApi()?.emailCheck(emailCheckRequest = emailCheckRequest)
    }

    suspend fun signupUser(signupRequest: SignupRequest):
            Response<SignupResponse>?{
        return UserApi.getApi()?.signupUser(signupRequest = signupRequest)
    }
}