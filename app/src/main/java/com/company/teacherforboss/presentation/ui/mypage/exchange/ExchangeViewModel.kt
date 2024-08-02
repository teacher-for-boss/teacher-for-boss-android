package com.company.teacherforboss.presentation.ui.mypage.exchange

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.teacherforboss.data.model.request.payment.RequestExchangeDto
import com.company.teacherforboss.data.model.response.payment.ResponseExchangeDto
import com.company.teacherforboss.domain.model.exchange.ExchangeRequestEntity
import com.company.teacherforboss.domain.model.exchange.ExchangeResponseEntity
import com.company.teacherforboss.domain.repository.PaymentRepository
import com.company.teacherforboss.domain.usecase.payment.ExchangeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val paymentRepository: PaymentRepository,
    private val exchangeUseCase: ExchangeUseCase
) : ViewModel() {
    val tpValue = MutableLiveData<String>(null)

    private var _convertedValue = MutableLiveData<String>()
    val convertedValue: LiveData<String>
        get() = _convertedValue

    private var _isExchangeButtonEnabled = MutableLiveData<Boolean>(false)
    val isExchangeButtonEnabled: LiveData<Boolean>
        get() = _isExchangeButtonEnabled

    private val _exchangeResult = MutableLiveData<ExchangeResponseEntity>()
    val exchangeResult: LiveData<ExchangeResponseEntity> get() = _exchangeResult

    init {
        tpValue.observeForever {
            _convertedValue.value = try {
                val tp = it.toDouble()
                val won = tp * 90
                "${won.toInt()}원* (수수료 10% 별도)"
            } catch (e: Exception) {
                "0원* (수수료 10% 별도)"
            }
            _isExchangeButtonEnabled.value = !(it.isNullOrBlank() || it.toDoubleOrNull() == 0.0)
        }
    }

    fun setTpValue(value: String) {
        tpValue.value = value
    }



    fun applyExchange(points: Int) {
        viewModelScope.launch {
            try {
                val request = ExchangeRequestEntity(points = points)
                val response = exchangeUseCase(request)
                _exchangeResult.postValue(response)
            } catch (e: Exception) {
            //
            }
        }
    }

}