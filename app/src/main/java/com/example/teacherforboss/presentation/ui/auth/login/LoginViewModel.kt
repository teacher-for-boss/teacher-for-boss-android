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
                //val res=UserApi.getApi()?.loginUser(loginRequest=loginRequest)

                if(response?.code()==200){
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

    fun socialLogin(email:String, name:String, phoneNumber: String, gender:Int?, birthDate: LocalDate?,imageUrl:String?){
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
                val response=userRepo.socialLogin(socialLoginRequest)

                if(response?.code()==200){
                    socialLoginResult.value=BaseResponse.Success(response.body())
                }
                else{
                    socialLoginResult.value=BaseResponse.Error(response?.message())
                }
            }catch (exception:Exception){
                socialLoginResult.value=BaseResponse.Error(exception.message)

            }
        }

    }


}