package com.company.teacherforboss.data.service

import com.company.teacherforboss.data.api.ApiClient
import com.company.teacherforboss.data.model.request.login.LoginRequest
import com.company.teacherforboss.data.model.request.login.SocialLoginRequest
import com.company.teacherforboss.data.model.request.signup.BusinessNumberCheckRequest
import com.company.teacherforboss.data.model.response.login.LoginResponse
import com.company.teacherforboss.data.model.response.login.socialLoginResponse
import com.company.teacherforboss.data.model.request.signup.EmailCheckRequest
import com.company.teacherforboss.data.model.response.signup.EmailCheckResponse
import com.company.teacherforboss.data.model.request.signup.EmailRequest
import com.company.teacherforboss.data.model.request.signup.NicknameRequest
import com.company.teacherforboss.data.model.response.signup.EmailResponse
import com.company.teacherforboss.data.model.response.signup.SignupResponse
import com.company.teacherforboss.data.model.request.signup.PhoneCheckRequest
import com.company.teacherforboss.data.model.response.signup.PhoneCheckResponse
import com.company.teacherforboss.data.model.request.signup.PhoneRequest
import com.company.teacherforboss.data.model.response.signup.NicknameResponse
import com.company.teacherforboss.data.model.request.signup.SignupBossRequest
import com.company.teacherforboss.data.model.request.signup.SignupTeacherRequest
import com.company.teacherforboss.data.model.request.signup.SocialSignupBossRequest
import com.company.teacherforboss.data.model.request.signup.SocialSignupTeacherRequest
import com.company.teacherforboss.data.model.response.auth.LogoutResponse
import com.company.teacherforboss.data.model.response.auth.WithdrawResponse
import com.company.teacherforboss.data.model.response.signup.BusinessNumberCheckResponse
import com.company.teacherforboss.data.model.response.signup.PhoneResponse
import com.company.teacherforboss.util.base.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {
    companion object{
        fun getApi(): AuthService?{
            return ApiClient.client?.create(AuthService::class.java)
        }
        const val MEMBER="members"
    }
    @POST("auth/login")
    suspend fun loginUser(
        @Body loginRequest: LoginRequest
    )
    : Response<LoginResponse>


    @POST("auth/reissue")
    suspend fun loginReissue(
        @Header("RefreshToken") refreshToken: String)
    : Response<LoginResponse>

    // social login
    @POST("auth/login/social")
    suspend fun socialLogin(
        @Query("socialType") socialType:Int,
        @Body socialLoginRequest: SocialLoginRequest
    )
    :Response<socialLoginResponse>

    // social signup-boss
    @POST("auth/login/social")
    suspend fun socialBossSignup(
        @Query("socialType") socialType:Int,
        @Body signupRequest: SocialSignupBossRequest
    ):Response<socialLoginResponse>

    // social signup-teacher
    @POST("auth/login/social")
    suspend fun socialTeacherSignup(
        @Query("socialType") socialType:Int,
        @Body signupRequest: SocialSignupTeacherRequest
    ):Response<socialLoginResponse>
    @POST("auth/email")
    suspend fun emailUser(
        @Body emailRequest: EmailRequest
    )
    : Response<EmailResponse>

    @POST("auth/email/check")
    suspend fun emailCheck(
        @Body emailCheckRequest: EmailCheckRequest
    )
    : Response<EmailCheckResponse>

    @POST("auth/signup")
    suspend fun signupBoss(
        @Body signupRequest: SignupBossRequest
    )
    : Response<SignupResponse>
    @POST("auth/signup")
    suspend fun signupTeacher(
        @Body signupRequest: SignupTeacherRequest
    )
    : Response<SignupResponse>

    @POST("auth/phone")
    suspend fun phoneUser(
        @Body phoneRequest: PhoneRequest
    )
    :Response<PhoneResponse>

    @POST("auth/phone/check")
    suspend fun phoneCheck(
        @Body phoneCheckRequest: PhoneCheckRequest
    )
    :Response<PhoneCheckResponse>

    @POST("auth/nickname/check")
    suspend fun nicknameUser(
        @Body nicknameRequest: NicknameRequest
    )
    :Response<NicknameResponse>
    @POST("auth/teacher/business-number/check")
    suspend fun businessNumCheck(
        @Body businessNumberCheckRequest: BusinessNumberCheckRequest
    ):BaseResponse<BusinessNumberCheckResponse>

    @POST("auth/logout")
    suspend fun logout(
        @Header("FCM-Token") fcmToken: String
    ):BaseResponse<LogoutResponse>

    @DELETE("auth/withdraw")
    suspend fun withdraw(
    ):BaseResponse<WithdrawResponse>

}