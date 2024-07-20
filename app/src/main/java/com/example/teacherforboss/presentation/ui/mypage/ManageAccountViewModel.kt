package com.example.teacherforboss.presentation.ui.mypage

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherforboss.data.tokenmanager.TokenManager
import com.example.teacherforboss.domain.model.auth.AccountEntity
import com.example.teacherforboss.domain.model.auth.LogoutResponseEntity
import com.example.teacherforboss.domain.usecase.Member.AccountUsecase
import com.example.teacherforboss.domain.usecase.auth.LogoutUsecase
import com.example.teacherforboss.domain.usecase.auth.WithdrawUsecase
import com.example.teacherforboss.domain.model.auth.WithdrawResponseEntity
import com.example.teacherforboss.util.view.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageAccountViewModel @Inject constructor(
    private val tokenManager:TokenManager,
    @ApplicationContext private val context: Context,
    private val accountUsecase: AccountUsecase,
    private val logoutUsecase: LogoutUsecase,
    private val withdrawUsecase: WithdrawUsecase
):ViewModel() {
    private val _getAccountState:MutableStateFlow<UiState<AccountEntity>> = MutableStateFlow(UiState.Empty)
    val getAccountState get() = _getAccountState.asStateFlow()

    private val _logoutState:MutableStateFlow<UiState<LogoutResponseEntity>> = MutableStateFlow(UiState.Empty)
    val logoutState get() = _logoutState.asStateFlow()

    private val _withdrawState:MutableStateFlow<UiState<WithdrawResponseEntity>> = MutableStateFlow(UiState.Empty)

    val withdrawState get() = _withdrawState.asStateFlow()


    fun getAccount(){
        viewModelScope.launch {
            accountUsecase().onSuccess { accountEntity->
                _getAccountState.value=UiState.Success(accountEntity)
            }.onFailure { exception: Throwable ->
                _getAccountState.value=UiState.Error(exception.message)
            }
        }
    }

    fun postLogout(){
        viewModelScope.launch {
            logoutUsecase().onSuccess { logoutResponseEntity->
                _logoutState.value=UiState.Success(logoutResponseEntity)
            }.onFailure { exception: Throwable ->
                _logoutState.value=UiState.Error(exception.message)
            }
        }
    }

    fun withdraw(){
        viewModelScope.launch {
            withdrawUsecase().onSuccess { withdrawEntity->
                _withdrawState.value=UiState.Success(withdrawEntity)
            }.onFailure { exception: Throwable ->
                _withdrawState.value=UiState.Error(exception.message)
            }
        }
    }
    fun clearTokens() {
        tokenManager.clearAccessToken(context)
        tokenManager.clearRefreshToken(context)
    }
}