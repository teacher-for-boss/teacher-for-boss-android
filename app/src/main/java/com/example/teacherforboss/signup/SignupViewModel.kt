package com.example.teacherforboss.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignupViewModel:ViewModel() {
    var liveEmail=MutableLiveData<String>("")
    var livePw=MutableLiveData<String>("")
    var liveRePw=MutableLiveData<String>("")

    val num_check=MutableLiveData<Boolean>(false)
    val eng_check=MutableLiveData<Boolean>(false)
    val special_check=MutableLiveData<Boolean>(false)
    val length_check=MutableLiveData<Boolean>(false)
    val rePw_check=MutableLiveData<Boolean>(false)
    val all_check=MutableLiveData<Boolean>(false)

//    private var _isEmailVerified=MutableLiveData<Boolean>(false)
//    val isEmailVerified:LiveData<Boolean>
//        get() = _isEmailVerified
//
//    val confirmedEmail=MutableLiveData<MutableMap<String,LiveData<Boolean>>>()
//
//    private var _isPhoneVerified=MutableLiveData<Boolean>(false)
//    val isPhoneVerified:LiveData<Boolean>
//        get()=_isPhoneVerified
//
//    val confirmedPhone=MutableLiveData<MutableMap<String,Boolean>>()
//
//    fun setEmailVerifiedStatus(isVefiried:Boolean){
//        _isEmailVerified.value=isVefiried
//    }
//
//    fun setPhoneVerifiedStatus(isVefiried: Boolean){
//        _isPhoneVerified.value=isVefiried
//    }

}