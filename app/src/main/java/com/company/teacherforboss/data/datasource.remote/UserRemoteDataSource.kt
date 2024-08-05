package com.company.teacherforboss.data.datasource.remote

import com.company.teacherforboss.data.model.request.login.LoginRequest
import com.company.teacherforboss.data.model.request.login.SocialLoginRequest
import com.company.teacherforboss.data.model.response.login.LoginResponse
import com.company.teacherforboss.data.model.response.login.socialLoginResponse
import com.company.teacherforboss.data.model.request.signup.EmailCheckRequest
import com.company.teacherforboss.data.model.response.signup.EmailCheckResponse
import com.company.teacherforboss.data.model.request.signup.EmailRequest
import com.company.teacherforboss.data.model.request.signup.NicknameRequest
import com.company.teacherforboss.data.model.request.signup.PhoneCheckRequest
import com.company.teacherforboss.data.model.request.signup.PhoneRequest
import com.company.teacherforboss.data.model.request.signup.SignupBossRequest
import com.company.teacherforboss.data.model.request.signup.SignupTeacherRequest
import com.company.teacherforboss.data.model.response.signup.EmailResponse
import com.company.teacherforboss.data.model.response.signup.NicknameResponse
import com.company.teacherforboss.data.model.response.signup.PhoneCheckResponse
import com.company.teacherforboss.data.model.response.signup.PhoneResponse
import com.company.teacherforboss.data.model.response.signup.SignupResponse
import retrofit2.Response

interface UserRemoteDataSource {
    suspend fun loginUser(loginRequest: LoginRequest): Response<LoginResponse>?
    suspend fun loginReissue(refreshToken:String): Response<LoginResponse>?
    suspend fun kakaoLogin(socialLoginRequest: SocialLoginRequest):Response<socialLoginResponse>?
    suspend fun naverLogin(socialLoginRequest: SocialLoginRequest):Response<socialLoginResponse>?
    suspend fun emailUser(emailRequest: EmailRequest):Response<EmailResponse>?
    suspend fun emailCheck(emailCheckRequest: EmailCheckRequest):Response<EmailCheckResponse>?
    suspend fun signupUser(signupRequest: SignupBossRequest):Response<SignupResponse>?
    suspend fun signupUser(signupRequest: SignupTeacherRequest):Response<SignupResponse>?

    suspend fun phoneUser(phoneRequest: PhoneRequest):Response<PhoneResponse>?

    suspend fun phoneCheck(phoneCheckRequest: PhoneCheckRequest):Response<PhoneCheckResponse>?

    suspend fun nicknameUser(nicknameRequest: NicknameRequest):Response<NicknameResponse>?


}