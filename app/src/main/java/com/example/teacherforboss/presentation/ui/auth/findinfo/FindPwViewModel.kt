package com.example.teacherforboss.presentation.ui.auth.findinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FindPwViewModel:ViewModel() {
    var livePhoneNumber=MutableLiveData<String>("")
    var liveEmail=MutableLiveData<String>("")


    var checkPhoneFormat=false
    private var _isPhoneVerified=MutableLiveData<Boolean>(false)
    val isPhoneVerified:LiveData<Boolean>
        get() = _isPhoneVerified

    private var _isEmailVerified=MutableLiveData<Boolean>(false)
    val isEmailVerified:LiveData<Boolean>
        get() = _isEmailVerified

}