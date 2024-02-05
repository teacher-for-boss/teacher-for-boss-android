package com.example.teacherforboss.data.model.response


//basic logig views에서 result.kt

//sealed class->enum과 비슷 but 상속 가능, 멤버값 변경 가능
//T,out-> 다양한 타입 사용 가능, data타입이 무엇이든 상관x
sealed class BaseResponse<out T>{
    data class Success<out T>(val data:T?=null): BaseResponse<T>()
    data class Loading(val nothing:Nothing?=null): BaseResponse<Nothing>()

    data class Error(val msg:String?): BaseResponse<Nothing>()
}