package com.example.teacherforboss.data.model.response.findInfo

import com.example.teacherforboss.data.model.response.InterfaceBasicResponse
import com.example.teacherforboss.data.model.response.LoginResponseInterface
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


//data class ResponseFindEmailDto(
//
//    @SerializedName("code")
//    var code:String,
//
//    @SerializedName("isSuccess")
//    var isSuccess:Boolean,
//
//    @SerializedName("message")
//    var message:String,
//
//    @SerializedName("result")
//    var result: Result?
//) {
//    data class Result(
//        @SerializedName("email")
//        val email: String?,
//
//        @SerializedName("createdAt")
//        val createdAt: String?
//    )
//
//}

data class ResponseFindEmailDto(

    @SerializedName("email")
    val email: String?,

    @SerializedName("createdAt")
    val createdAt: String?
)


