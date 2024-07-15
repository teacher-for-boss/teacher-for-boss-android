package com.example.teacherforboss.presentation.ui.mypage.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class AccountViewModel @Inject constructor() : ViewModel() {

    var _chosenBank = MutableLiveData<String>("")
    val chosenBank: LiveData<String>
        get() = _chosenBank

    var _etInputAccount = MutableLiveData<String>("")
    val etInputAccount: LiveData<String>
        get() = _etInputAccount

    var _etInputName = MutableLiveData<String>("")
    val etInputName: LiveData<String>
        get() = _etInputName

    var enableNext = MutableLiveData<Boolean>(false)

    fun validateFields() {
        enableNext.value = !(_chosenBank.value.isNullOrEmpty() ||
                _etInputAccount.value.isNullOrEmpty() ||
                _etInputName.value.isNullOrEmpty())
    }

    fun setChosenBank(bank: String) {
        _chosenBank.value = bank
        validateFields()
    }

    fun setInputAccount(account: String) {
        _etInputAccount.value = account
        validateFields()
    }

    fun setInputName(name: String) {
        _etInputName.value = name
        validateFields()
    }
}