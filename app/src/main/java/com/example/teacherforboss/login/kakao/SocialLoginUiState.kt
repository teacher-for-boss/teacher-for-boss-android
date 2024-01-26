package com.example.teacherforboss.login.kakao

sealed interface SocialLoginUiState{
    object LoginSuccess:SocialLoginUiState
    object LoginFail:SocialLoginUiState
    object Idle:SocialLoginUiState
    object KakaoLogin:SocialLoginUiState
}