package com.example.teacherforboss.domain.repository

import com.example.teacherforboss.data.model.request.login.LoginRequest
import com.example.teacherforboss.data.model.request.login.SocialLoginRequest
import com.example.teacherforboss.data.model.request.signup.BusinessNumberCheckRequest
import com.example.teacherforboss.data.model.response.login.LoginResponse
import com.example.teacherforboss.data.model.response.login.socialLoginResponse
import com.example.teacherforboss.data.model.request.signup.EmailCheckRequest
import com.example.teacherforboss.data.model.response.signup.EmailCheckResponse
import com.example.teacherforboss.data.model.request.signup.EmailRequest
import com.example.teacherforboss.data.model.request.signup.NicknameRequest
import com.example.teacherforboss.data.model.response.signup.EmailResponse
import com.example.teacherforboss.data.model.response.signup.SignupResponse
import com.example.teacherforboss.data.model.request.signup.PhoneCheckRequest
import com.example.teacherforboss.data.model.response.signup.PhoneCheckResponse
import com.example.teacherforboss.data.model.request.signup.PhoneRequest
import com.example.teacherforboss.data.model.response.signup.NicknameResponse
import com.example.teacherforboss.data.model.request.signup.SignupBossRequest
import com.example.teacherforboss.data.model.request.signup.SignupTeacherRequest
import com.example.teacherforboss.data.model.request.signup.SocialSignupBossRequest
import com.example.teacherforboss.data.model.request.signup.SocialSignupTeacherRequest
import com.example.teacherforboss.data.model.response.signup.BusinessNumberCheckResponse
import com.example.teacherforboss.data.model.response.signup.PhoneResponse
import com.example.teacherforboss.util.base.BaseResponse
import retrofit2.Response

// UserRepository.kt
interface UserRepository {
    suspend fun loginUser(loginRequest: LoginRequest): Response<LoginResponse>?
    suspend fun loginReissue(refreshToken: String): Response<LoginResponse>?
    suspend fun kakaoLogin(socialLoginRequest: SocialLoginRequest): Response<socialLoginResponse>?
    suspend fun naverLogin(socialLoginRequest: SocialLoginRequest): Response<socialLoginResponse>?

    suspend fun socialBossSignup(socialType:Int,signupRequest: SocialSignupBossRequest): Response<socialLoginResponse>?

    suspend fun socialTeacherSignup(sociType:Int,signupRequest: SocialSignupTeacherRequest): Response<socialLoginResponse>?
    suspend fun emailUser(emailRequest: EmailRequest): Response<EmailResponse>?
    suspend fun emailCheck(emailCheckRequest: EmailCheckRequest): Response<EmailCheckResponse>?
    suspend fun signupBoss(signupRequest: SignupBossRequest): Response<SignupResponse>?
    suspend fun signupTeacher(signupRequest: SignupTeacherRequest): Response<SignupResponse>?

    suspend fun phoneUser(phoneRequest: PhoneRequest):Response<PhoneResponse>?

    suspend fun phoneCheck(phoneCheckRequest: PhoneCheckRequest):Response<PhoneCheckResponse>?

    suspend fun nicknameUser(nicknameRequest: NicknameRequest):Response<NicknameResponse>?

    suspend fun businessNumCheck(businessNumberCheckRequest: BusinessNumberCheckRequest):BaseResponse<BusinessNumberCheckResponse>

}
