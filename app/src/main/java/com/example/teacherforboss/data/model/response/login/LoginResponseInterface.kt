package com.example.teacherforboss.data.model.response.login

interface LoginResponseInterface {
    var code:String
    var isSuccess:Boolean
    var message:String
    var result: Result
    data class Result(
        val role:String,
        val name:String,
        var accessToken:String,
        var refreshToken:String,
        var email:String? //social시 사용
    )

}