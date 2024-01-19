package com.example.teacherforboss.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignupViewModel:ViewModel() {
    var livePw=MutableLiveData<String>("")
    var liveRePw=MutableLiveData<String>("")

    val num_check=MutableLiveData<Boolean>(false)
    val eng_check=MutableLiveData<Boolean>(false)
    val special_check=MutableLiveData<Boolean>(false)
    val length_check=MutableLiveData<Boolean>(false)
    val rePw_check=MutableLiveData<Boolean>(false)
    val all_check=MutableLiveData<Boolean>(false)

    private var _isEmailVerified=MutableLiveData<Boolean>(false)
    val isEmailVerified:LiveData<Boolean>
        get() = _isEmailVerified

    fun setEmailVerifiedStatus(isVefiried:Boolean){
        _isEmailVerified.value=isVefiried
    }

}