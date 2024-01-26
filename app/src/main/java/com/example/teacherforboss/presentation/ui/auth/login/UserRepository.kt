package com.example.teacherforboss.presentation.ui.auth.login

import com.example.teacherforboss.signup.api.EmailCheckRequest
import com.example.teacherforboss.signup.api.EmailCheckResponse
import com.example.teacherforboss.signup.api.EmailRequest
import com.example.teacherforboss.signup.api.EmailResponse
import com.example.teacherforboss.signup.api.SignupRequest
import com.example.teacherforboss.signup.api.SignupResponse
import retrofit2.Response

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