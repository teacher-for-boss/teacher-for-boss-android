package com.example.teacherforboss.presentation.ui.auth.login.social

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SocialLoginViewModel:ViewModel() {
    private val _socialLoginUiState= MutableStateFlow<SocialLoginUiState>(SocialLoginUiState.Idle)

    val socialLoginUiState=_socialLoginUiState.asStateFlow()//읽기전용 상태

    fun kakaoLogin(){
        _socialLoginUiState.value= SocialLoginUiState.KakaoLogin
    }

    fun naverLogin(){
        _socialLoginUiState.value= SocialLoginUiState.NaverLogin
    }

    fun kakaoLoginSuccess(){
        _socialLoginUiState.tryEmit(SocialLoginUiState.KakaoLoginSuccess)
    }
    fun naverLoginSuccess(){
        _socialLoginUiState.tryEmit(SocialLoginUiState.NaverLoginSuccess)
    }

    fun kakaoLoginFail(){
        _socialLoginUiState.tryEmit(SocialLoginUiState.LoginFail)
    }

    fun naverLoginFail(){
        _socialLoginUiState.tryEmit(SocialLoginUiState.LoginFail)
    }

    fun setUiStateIdle(){
        _socialLoginUiState.tryEmit(SocialLoginUiState.Idle)
    }

}