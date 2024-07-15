package com.example.teacherforboss.presentation.ui.mypage.exchange

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ExchangeViewModel : ViewModel() {
    val tpValue = MutableLiveData<String>(null)

    var _convertedValue = MutableLiveData<String>()
    val convertedValue: LiveData<String>
        get() = _convertedValue

    var _isExchangeButtonEnabled = MutableLiveData<Boolean>(false)
    val isExchangeButtonEnabled: LiveData<Boolean>
        get() = _isExchangeButtonEnabled

    init {
        tpValue.observeForever {
            _convertedValue.value = try {
                val tp = it.toDouble()
                val won = tp * 90
                "${won.toInt()}원* (수수료 10% 별도)"
            } catch (e: Exception) {
                "0원* (수수료 10% 별도)"
            }
            _isExchangeButtonEnabled.value = !it.isNullOrBlank()
        }
    }

    fun setTpValue(value: String) {
        tpValue.value = value
    }
}