package com.company.teacherforboss.presentation.ui.auth.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.teacherforboss.data.model.request.login.LoginRequest
import com.company.teacherforboss.data.model.request.login.SocialLoginRequest
import com.company.teacherforboss.data.model.response.login.LoginResponse
import com.company.teacherforboss.data.model.response.BaseResponse
import com.company.teacherforboss.data.model.response.login.LoginResponseInterface
import com.company.teacherforboss.data.repository.UserRepositoryImpl
import com.company.teacherforboss.data.model.response.login.socialLoginResponse
import com.company.teacherforboss.data.tokenmanager.TokenManager
import com.company.teacherforboss.util.base.ConstsUtils.Companion.SIGNUP_SOCIAL_KAKAO
import com.company.teacherforboss.util.base.ConstsUtils.Companion.SIGNUP_SOCIAL_NAVER
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val tokenManager:TokenManager,
    @ApplicationContext private val context: Context
): ViewModel(){
    val loginResult: MutableLiveData<BaseResponse<LoginResponse>> = MutableLiveData()
    val socialLoginResult: MutableLiveData<BaseResponse<socialLoginResponse>> = MutableLiveData()
    val userRepo= UserRepositoryImpl()

    var _isSocialLoginSignup=MutableLiveData<Boolean>(false)
    val isSocialLoginSinup : LiveData<Boolean>
        get() = _isSocialLoginSignup

    fun loginUser(email:String,pwd:String){
        loginResult.value= BaseResponse.Loading()

        viewModelScope.launch {
            try{
                val loginRequest= LoginRequest(
                    email=email,
                    password=pwd
                )
                val response=userRepo.loginUser(loginRequest=loginRequest)
                if(response?.body()?.code=="COMMON200"){
                    loginResult.value= BaseResponse.Success(response.body())
                }
                else{
                    loginResult.value= BaseResponse.Error(response?.message())
                }
            }catch(ex:Exception){
                loginResult.value= BaseResponse.Error(ex.message.toString())
            }
        }
    }

    fun socialLogin(type:String,email:String){
        socialLoginResult.value= BaseResponse.Loading()

        viewModelScope.launch{
            try{
                val socialLoginRequest= SocialLoginRequest(
                    email=email
                )

                var response: Response<socialLoginResponse>?=null

                if(type== SIGNUP_SOCIAL_KAKAO) {response=userRepo.kakaoLogin(socialLoginRequest)}
                else if(type== SIGNUP_SOCIAL_NAVER){
                    response=userRepo.naverLogin(socialLoginRequest)
                }
                else{
                    socialLoginResult.value= BaseResponse.Error("not kakao or naver")
                }

                if(response?.body()?.code=="COMMON200"){
                    socialLoginResult.value= BaseResponse.Success(response.body())
                }
                else{
                    Log.d("social?",response?.body().toString())
                    socialLoginResult.value= BaseResponse.Error(response?.message())
                }
            }catch (exception:Exception){
                socialLoginResult.value= BaseResponse.Error(exception.message)

            }
        }

    }

    fun getAcessToken()=tokenManager.getAccessToken(context)


    fun <T: LoginResponseInterface>saveToken(data: T?){
        if(!data?.result?.accessToken.isNullOrEmpty()){
            data?.result?.accessToken.let{
                tokenManager.saveAccessToken(context, it!!)
            }

        }
        if(!data?.result?.refreshToken.isNullOrEmpty()){
            data?.result?.refreshToken.let{
                TokenManager.saveRefreshToken(context, it!!)
            }
        }
    }

}