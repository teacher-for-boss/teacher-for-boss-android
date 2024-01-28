package com.example.teacherforboss.presentation.ui.auth.login.social

sealed interface SocialLoginUiState{
    object LoginSuccess: SocialLoginUiState
    object LoginFail: SocialLoginUiState
    object Idle: SocialLoginUiState
    object KakaoLogin: SocialLoginUiState
}