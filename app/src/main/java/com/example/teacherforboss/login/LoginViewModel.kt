package com.example.teacherforboss.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel(): ViewModel(){
    val userRepo=UserRepository()//viewmodel 생성자?
    val loginResult: MutableLiveData<BaseResponse<LoginResponse>> = MutableLiveData()

    fun loginUser(email:String,pwd:String){
        loginResult.value=BaseResponse.Loading()

        viewModelScope.launch {
            try{
                val loginRequest=LoginRequest(
                    email=email,
                    password=pwd
                )
                val response=userRepo.loginUser(loginRequest=loginRequest)
                //val res=UserApi.getApi()?.loginUser(loginRequest=loginRequest)

                if(response?.code()==200){
                    loginResult.value=BaseResponse.Success(response.body())
                }
                else{
                    loginResult.value=BaseResponse.Error(response?.message())
                }
            }catch(ex:Exception){
                loginResult.value=BaseResponse.Error(ex.message)
            }
        }
    }


}