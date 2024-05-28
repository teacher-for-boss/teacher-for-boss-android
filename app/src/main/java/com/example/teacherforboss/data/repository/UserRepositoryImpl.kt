package com.example.teacherforboss.data.repository

import com.example.teacherforboss.data.service.AuthService
import com.example.teacherforboss.data.model.request.login.LoginRequest
import com.example.teacherforboss.data.model.request.login.SocialLoginRequest
import com.example.teacherforboss.data.model.request.signup.BusinessNumberCheckRequest
import com.example.teacherforboss.data.model.response.login.LoginResponse
import com.example.teacherforboss.data.model.response.login.socialLoginResponse
import com.example.teacherforboss.domain.repository.UserRepository
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
import com.example.teacherforboss.data.model.response.signup.BusinessNumberCheckResponse
import com.example.teacherforboss.data.model.response.signup.PhoneResponse
import com.example.teacherforboss.util.base.BaseResponse
import retrofit2.Response

class UserRepositoryImpl: UserRepository {
     override suspend fun loginUser(loginRequest: LoginRequest):
            Response<LoginResponse>?{
        return AuthService.getApi()?.loginUser(loginRequest=loginRequest)
    }
    override suspend fun loginReissue(refreshToken:String):
            Response<LoginResponse>?{
        return AuthService.getApi()?.loginReissue(refreshToken =refreshToken)
    }

    override suspend fun kakaoLogin(socialLoginRequest: SocialLoginRequest):
            Response<socialLoginResponse>?{
        return AuthService.getApi()?.socialLogin(socialType=2, socialLoginRequest=socialLoginRequest)
    }

    override suspend fun naverLogin(socialLoginRequest: SocialLoginRequest):
            Response<socialLoginResponse>?{
        return AuthService.getApi()?.socialLogin(socialType=3, socialLoginRequest=socialLoginRequest)
    }

    override suspend fun emailUser(emailRequest: EmailRequest):
            Response<EmailResponse>?{
        return AuthService.getApi()?.emailUser(emailRequest = emailRequest)
    }

    override suspend fun emailCheck(emailCheckRequest: EmailCheckRequest):
            Response<EmailCheckResponse>?{
        return AuthService.getApi()?.emailCheck(emailCheckRequest = emailCheckRequest)
    }

    override suspend fun signupBoss(signupRequest: SignupBossRequest): Response<SignupResponse>? {
        return AuthService.getApi()?.signupBoss(signupRequest = signupRequest)
    }

    override suspend fun signupTeacher(signupRequest: SignupTeacherRequest): Response<SignupResponse>? {
        return AuthService.getApi()?.signupTeacher(signupRequest = signupRequest)
    }
    override suspend fun phoneUser(phoneRequest: PhoneRequest):
            Response<PhoneResponse>?{
        return AuthService.getApi()?.phoneUser(phoneRequest = phoneRequest)
    }

    override suspend fun phoneCheck(phoneCheckRequest: PhoneCheckRequest):
            Response<PhoneCheckResponse>?{
        return AuthService.getApi()?.phoneCheck(phoneCheckRequest = phoneCheckRequest)
    }
    override suspend fun nicknameUser(nicknameRequest: NicknameRequest):
            Response<NicknameResponse>?{
        return AuthService.getApi()?.nicknameUser(nicknameRequest = nicknameRequest)
    }
    override suspend fun businessNumCheck(businessNumberCheckRequest: BusinessNumberCheckRequest): BaseResponse<BusinessNumberCheckResponse> {
        return AuthService.getApi()!!.businessNumCheck(businessNumberCheckRequest=businessNumberCheckRequest)
    }

}