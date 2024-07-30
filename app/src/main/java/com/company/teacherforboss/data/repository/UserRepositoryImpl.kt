package com.company.teacherforboss.data.repository

import com.company.teacherforboss.data.service.AuthService
import com.company.teacherforboss.data.model.request.login.LoginRequest
import com.company.teacherforboss.data.model.request.login.SocialLoginRequest
import com.company.teacherforboss.data.model.request.signup.BusinessNumberCheckRequest
import com.company.teacherforboss.data.model.response.login.LoginResponse
import com.company.teacherforboss.data.model.response.login.socialLoginResponse
import com.company.teacherforboss.domain.repository.UserRepository
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
import com.company.teacherforboss.data.model.response.signup.BusinessNumberCheckResponse
import com.company.teacherforboss.data.model.response.signup.PhoneResponse
import com.company.teacherforboss.util.base.BaseResponse
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
    override suspend fun socialBossSignup(socialType:Int,signupRequest: SocialSignupBossRequest):
            Response<socialLoginResponse>? {
        return AuthService.getApi()?.socialBossSignup(socialType=socialType,signupRequest=signupRequest)
    }

    override suspend fun socialTeacherSignup(socialType:Int,signupRequest: SocialSignupTeacherRequest):
            Response<socialLoginResponse>? {
        return AuthService.getApi()?.socialTeacherSignup(socialType=socialType,signupRequest=signupRequest)
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