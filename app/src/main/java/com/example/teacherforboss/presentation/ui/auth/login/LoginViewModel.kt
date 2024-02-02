package com.example.teacherforboss.presentation.ui.auth.login

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherforboss.presentation.ui.auth.common.BaseResponse
import com.example.teacherforboss.presentation.ui.auth.common.UserRepository
import com.example.teacherforboss.presentation.ui.auth.login.social.socialLoginRequest
import com.example.teacherforboss.presentation.ui.auth.login.social.socialLoginResponse
import kotlinx.coroutines.launch
import org.apache.commons.lang3.mutable.Mutable
import retrofit2.Response
import java.time.LocalDate
import java.util.Date

class LoginViewModel(): ViewModel(){
    val userRepo= UserRepository()//viewmodel 생성자?
    val loginResult: MutableLiveData<BaseResponse<LoginResponse>> = MutableLiveData()
    val socialLoginResult: MutableLiveData<BaseResponse<socialLoginResponse>> = MutableLiveData()

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
                loginResult.value= BaseResponse.Error(ex.message)
            }
        }
    }

    fun socialLogin(type:String,email:String, name:String, phoneNumber: String, gender:Int?, birthDate: String?,imageUrl:String?){
        socialLoginResult.value=BaseResponse.Loading()

        viewModelScope.launch{
            try{
                val socialLoginRequest=socialLoginRequest(
                    email=email,
                    name=name,
                    phone=phoneNumber,
                    gender=gender,
                    birthDate = birthDate,
                    profileImg = imageUrl
                )

                var response: Response<socialLoginResponse>?=null

                if(type=="kakao") {response=userRepo.kakaoLogin(socialLoginRequest)}
                else if(type=="naver"){
                    response=userRepo.naverLogin(socialLoginRequest)
                }
                else{
                    socialLoginResult.value=BaseResponse.Error("not kakao or naver")
                }

                if(response?.body()?.code=="COMMON200"){
                    socialLoginResult.value=BaseResponse.Success(response.body())
                }
                else{
                    Log.d("social?",response?.body().toString())
                    socialLoginResult.value=BaseResponse.Error(response?.message())
                }
            }catch (exception:Exception){
                socialLoginResult.value=BaseResponse.Error(exception.message)

            }
        }

    }


}