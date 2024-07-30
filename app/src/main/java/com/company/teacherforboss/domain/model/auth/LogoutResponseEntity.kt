package com.company.teacherforboss.domain.model.auth

data class LogoutResponseEntity(
    val email:String,
    val accessToken:String,
    val logoutAt:String
)