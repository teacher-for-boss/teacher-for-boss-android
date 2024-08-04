package com.company.teacherforboss.presentation.ui.mypage.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.teacherforboss.domain.model.payment.BankAccountChangeRequestEntity
import com.company.teacherforboss.domain.model.payment.BankAccountChangeResponseEntity
import com.company.teacherforboss.domain.model.payment.BankAccountResponseEntity
import com.company.teacherforboss.domain.usecase.payment.BankAccountChangeUseCase
import com.company.teacherforboss.domain.usecase.payment.BankAccountUseCase
import com.company.teacherforboss.util.view.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val bankAccountUseCase: BankAccountUseCase,
    private val bankAccountChangeUseCase: BankAccountChangeUseCase
) : ViewModel() {

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

    init {
        _chosenBank.observeForever { validateFields() }
        _etInputAccount.observeForever { validateFields() }
        _etInputName.observeForever { validateFields() }
    }

    private fun validateFields() {
        enableNext.value = !(_chosenBank.value.isNullOrEmpty() ||
                _etInputAccount.value.isNullOrEmpty() ||
                _etInputName.value.isNullOrEmpty())
    }

    fun setChosenBank(bank: String) {
        _chosenBank.value = bank
    }

    fun setInputAccount(account: String) {
        _etInputAccount.value = account
    }

    fun setInputName(name: String) {
        _etInputName.value = name
    }
    private val _bankAccountInfoState = MutableStateFlow<UiState<BankAccountResponseEntity>>(UiState.Empty)
    val bankAccountInfoState get() = _bankAccountInfoState.asStateFlow()

    private val _bankAccountChangeState = MutableStateFlow<UiState<BankAccountChangeResponseEntity>>(UiState.Empty)
    val bankAccountChangeState get() = _bankAccountChangeState.asStateFlow()

    fun getUserBankAccount() {
        viewModelScope.launch {
            bankAccountUseCase().onSuccess { bankAccountResponseEntity ->
                _bankAccountInfoState.value=UiState.Success(bankAccountResponseEntity)
            }.onFailure { exception: Throwable ->
                _bankAccountInfoState.value=UiState.Error(exception.message)
            }
        }
    }
    fun changeUserBankAccount(){
        viewModelScope.launch {
            try{
                val bankAccountChangeResponseEntity = bankAccountChangeUseCase(BankAccountChangeRequestEntity(
                    bank = chosenBank.value!!,
                    accountNumber = etInputAccount.value!!,
                    accountHolder = etInputName.value!!
                ))
                _bankAccountChangeState.value = UiState.Success(bankAccountChangeResponseEntity)

            }catch (ex:Exception){_bankAccountChangeState.value=UiState.Error(ex.message)}
        }
    }
}