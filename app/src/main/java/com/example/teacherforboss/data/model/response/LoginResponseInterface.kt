package com.example.teacherforboss.data.model.response

interface LoginResponseInterface {
    var code:String
    var isSuccess:Boolean
    var message:String
    var result: Result
    data class Result(
        var accessToken:String,
        var refreshToken:String,
        var email:String? //social시 사용
    )

}