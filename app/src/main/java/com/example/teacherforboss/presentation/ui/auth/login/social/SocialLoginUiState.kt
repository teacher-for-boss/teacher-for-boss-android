package com.example.teacherforboss.presentation.ui.auth.login.social

sealed interface SocialLoginUiState{
    object KakaoLogin: SocialLoginUiState
    object NaverLogin: SocialLoginUiState
    object KakaoLoginSuccess: SocialLoginUiState //안써도 될것 같음..고민
    object NaverLoginSuccess: SocialLoginUiState
    object LoginFail: SocialLoginUiState
    object Idle: SocialLoginUiState

}