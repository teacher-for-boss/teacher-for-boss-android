package com.example.teacherforboss.presentation.ui.auth.login.kakao

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SocialLoginViewModel:ViewModel() {
    private val _socialLoginUiState= MutableStateFlow<SocialLoginUiState>(SocialLoginUiState.Idle)

    val socialLoginUiState=_socialLoginUiState.asStateFlow()//읽기전용 상태

    fun kakaoLogin(){
        _socialLoginUiState.value= SocialLoginUiState.KakaoLogin
    }
    fun kakaoLoginSuccess(){
        _socialLoginUiState.tryEmit(SocialLoginUiState.LoginSuccess)
    }
    fun kakaoLoginFail(){
        _socialLoginUiState.tryEmit(SocialLoginUiState.LoginFail)
    }

    fun setUiStateIdle(){
        _socialLoginUiState.tryEmit(SocialLoginUiState.Idle)
    }
}