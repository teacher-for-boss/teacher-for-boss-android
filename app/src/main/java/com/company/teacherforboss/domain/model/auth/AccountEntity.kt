package com.company.teacherforboss.domain.model.auth

data class AccountEntity (
    val memberId:Long,
    val email:String,
    val phone:String?,
    val loginType:String,
)