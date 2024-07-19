package com.example.teacherforboss.presentation.ui.mypage

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherforboss.data.tokenmanager.TokenManager
import com.example.teacherforboss.domain.model.auth.LogoutResponseEntity
import com.example.teacherforboss.domain.usecase.auth.LogoutUsecase
import com.example.teacherforboss.util.view.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageAccountViewModel @Inject constructor(
    private val logoutUsecase: LogoutUsecase,
    private val tokenManager:TokenManager,
    @ApplicationContext private val context: Context
):ViewModel() {
    private val _logoutState:MutableStateFlow<UiState<LogoutResponseEntity>> = MutableStateFlow(UiState.Empty)
    val logoutState get() = _logoutState.asStateFlow()

    fun postLogout(){
        viewModelScope.launch {
            logoutUsecase().onSuccess { logoutResponseEntity->
                _logoutState.value=UiState.Success(logoutResponseEntity)
            }.onFailure { exception: Throwable ->
                _logoutState.value=UiState.Error(exception.message)
            }
        }
    }
    fun clearTokens() {
        tokenManager.clearAccessToken(context)
        tokenManager.clearRefreshToken(context)
    }
}