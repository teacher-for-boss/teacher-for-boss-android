package com.example.teacherforboss.presentation.ui.auth.findinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FindPwViewModel:ViewModel() {
    //signupViewModel과 비슷-> 모듈화? 좀더 효율적으로 관리할방법 고려하기
    var livePhoneNumber=MutableLiveData<String>("")
    val phoneNumber:LiveData<String>
        get() = livePhoneNumber

    var liveEmail=MutableLiveData<String>("")
    val email: LiveData<String>
        get() = liveEmail

    var livePw= MutableLiveData<String>("")
    var liveRePw= MutableLiveData<String>("")
    val pw: LiveData<String>
        get() = livePw
    val rePw: LiveData<String>
        get() = liveRePw

    private var _isPhoneVerified=MutableLiveData<Boolean>(false)
    val isPhoneVerified:LiveData<Boolean>
        get() = _isPhoneVerified

    private var _isEmailVerified=MutableLiveData<Boolean>(false)
    val isEmailVerified:LiveData<Boolean>
        get() = _isEmailVerified

    val num_check= MutableLiveData<Boolean>(false)
    val eng_check= MutableLiveData<Boolean>(false)
    val special_check= MutableLiveData<Boolean>(false)
    val length_check= MutableLiveData<Boolean>(false)
    val rePw_check= MutableLiveData<Boolean>(false)
    val all_check= MutableLiveData<Boolean>(false)

}