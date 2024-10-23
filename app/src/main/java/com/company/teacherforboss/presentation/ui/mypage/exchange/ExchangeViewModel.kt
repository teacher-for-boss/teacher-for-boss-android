package com.company.teacherforboss.presentation.ui.mypage.exchange

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.teacherforboss.GlobalApplication
import com.company.teacherforboss.data.model.request.payment.RequestExchangeDto
import com.company.teacherforboss.data.model.response.payment.ResponseExchangeDto
import com.company.teacherforboss.domain.model.exchange.ExchangeListRequestEntity
import com.company.teacherforboss.domain.model.exchange.ExchangeListResponseEntity
import com.company.teacherforboss.domain.model.exchange.ExchangeRequestEntity
import com.company.teacherforboss.domain.model.exchange.ExchangeResponseEntity
import com.company.teacherforboss.domain.model.payment.BankAccountResponseEntity
import com.company.teacherforboss.domain.model.payment.TeacherPointResponseEntity
import com.company.teacherforboss.domain.repository.PaymentRepository
import com.company.teacherforboss.domain.usecase.Member.AccountUsecase
import com.company.teacherforboss.domain.usecase.payment.BankAccountUseCase
import com.company.teacherforboss.domain.usecase.payment.ExchangeListUseCase
import com.company.teacherforboss.domain.usecase.payment.ExchangeUseCase
import com.company.teacherforboss.domain.usecase.payment.TeacherPointUseCase
import com.company.teacherforboss.util.base.LocalDataSource
import com.company.teacherforboss.util.view.UiState
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    application: Application,
    private val paymentRepository: PaymentRepository,
    private val exchangeUseCase: ExchangeUseCase,
    private val accountUseCase: BankAccountUseCase,
    private val teacherPointUseCase: TeacherPointUseCase,
    private val exchangeListUseCase: ExchangeListUseCase
) : ViewModel() {

    private val _getAccountState: MutableStateFlow<UiState<BankAccountResponseEntity>> = MutableStateFlow(UiState.Empty)
    val getAccountState get() = _getAccountState.asStateFlow()

    private val _getTeacherPointState: MutableStateFlow<UiState<TeacherPointResponseEntity>> = MutableStateFlow(UiState.Empty)
    val getTeacherPointState get() = _getTeacherPointState.asStateFlow()

    private val _getExchangeUiState: MutableStateFlow<UiState<ExchangeResponseEntity>> = MutableStateFlow(UiState.Empty)
    val getExchangeUiState get() = _getExchangeUiState.asStateFlow()

    private val _getExchangeListUiState: MutableStateFlow<UiState<ExchangeListResponseEntity>> = MutableStateFlow(UiState.Empty)
    val getExchangeListUiState get() = _getExchangeListUiState.asStateFlow()

    var _lastExchangeId = MutableLiveData<Long>(0)
    val lastExchangeId: LiveData<Long> get() = _lastExchangeId

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> get() = _userName

    private val _currentTeacherPoint: MutableLiveData<Int> = MutableLiveData(0)
    val currentTeacherPoint: LiveData<Int>
        get() = _currentTeacherPoint

    val tpValue = MutableLiveData<String>(null)

    private var _convertedValue = MutableLiveData<String>()
    val convertedValue: LiveData<String>
        get() = _convertedValue

    private var _isExchangeButtonEnabled = MutableLiveData<Boolean>(false)
    val isExchangeButtonEnabled: LiveData<Boolean>
        get() = _isExchangeButtonEnabled

    private val _exchangeResult = MutableLiveData<ExchangeResponseEntity>()
    val exchangeResult: LiveData<ExchangeResponseEntity> get() = _exchangeResult

    private val _bank = MutableLiveData<String>()
    val bank: LiveData<String> get() = _bank

    private val _accountHolder = MutableLiveData<String>()
    val accountHolder: LiveData<String> get() = _accountHolder

    private val _accountNumber = MutableLiveData<String>()
    val accountNumber: LiveData<String> get() = _accountNumber

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
        getAccountInfo()
        getTeacherPoint()
    }

    fun setTpValue(value: String) {
        tpValue.value = value
    }

    fun applyExchange(points: Int) {
        viewModelScope.launch {
            val request = ExchangeRequestEntity(points = points)
            _getExchangeUiState.value = UiState.Loading
            exchangeUseCase(request).onSuccess { exchangeResponse ->
                _exchangeResult.postValue(exchangeResponse)
                _getExchangeUiState.value = UiState.Success(exchangeResponse)
            }.onFailure { throwable ->
                _getExchangeUiState.value = UiState.Error(throwable.message)
            }
        }
    }

    fun getExchangeList() {
        viewModelScope.launch {
            exchangeListUseCase(exchangeListRequestEntity = ExchangeListRequestEntity(
                lastExchangeId = getLastExchangeId(),
                size = 10)).onSuccess { exchangeListEntity ->
                _getExchangeListUiState.value = UiState.Success(exchangeListEntity)
            }.onFailure { exception: Throwable ->
                _getExchangeListUiState.value = UiState.Error(exception.message)
            }
        }
    }

    fun getLastExchangeId(): Long {
        return lastExchangeId.value!!
    }

    fun setLastExchangeId(id: Long) {
        _lastExchangeId.value = id
    }

    fun updateGetExchangeUiState(data: ExchangeResponseEntity) {
        _getExchangeUiState.value = UiState.Success(data)
    }

    private fun updateTeacherPoint(point: Int) {
        _currentTeacherPoint.value = point
    }

    fun setUserName(userName: String) {
        _userName.value = userName
    }

    private fun getAccountInfo() {
        viewModelScope.launch {
            accountUseCase().onSuccess { bankAccountResponseEntity ->
                _bank.postValue(bankAccountResponseEntity.bank)
                _accountHolder.postValue(bankAccountResponseEntity.accountHolder)
                _accountNumber.postValue(bankAccountResponseEntity.accountNumber)
                _getAccountState.value = UiState.Success(bankAccountResponseEntity)
            }.onFailure { exception: Throwable ->
                _getAccountState.value = UiState.Error(exception.message)
            }
        }
    }

    fun getTeacherPoint() {
        viewModelScope.launch {
            teacherPointUseCase().onSuccess { teacherPointResponseEntity ->
                updateTeacherPoint(teacherPointResponseEntity.points)
                _getTeacherPointState.value = UiState.Success(teacherPointResponseEntity)
            }.onFailure { exception: Throwable ->
                _getTeacherPointState.value = UiState.Error(exception.message)
            }
        }
    }

}