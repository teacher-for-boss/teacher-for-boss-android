package com.example.teacherforboss.presentation.ui.auth.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherforboss.data.model.request.login.LoginRequest
import com.example.teacherforboss.data.model.request.login.SocialLoginRequest
import com.example.teacherforboss.data.model.response.login.LoginResponse
import com.example.teacherforboss.data.model.response.BaseResponse
import com.example.teacherforboss.data.repository.UserRepositoryImpl
import com.example.teacherforboss.data.model.response.login.socialLoginResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(
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
                    Log.d("login test",response?.body().toString())
                    Log.d("login test",response?.code().toString())
                    loginResult.value= BaseResponse.Error(response?.message())
                }
            }catch(ex:Exception){
                loginResult.value= BaseResponse.Error(ex.message.toString())
//                loginResult.value= BaseResponse.Error(ex.message)
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

                if(type=="kakao") {response=userRepo.kakaoLogin(socialLoginRequest)}
                else if(type=="naver"){
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


}