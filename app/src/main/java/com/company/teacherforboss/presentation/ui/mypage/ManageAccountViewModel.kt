package com.company.teacherforboss.presentation.ui.mypage

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.teacherforboss.data.tokenmanager.TokenManager
import com.company.teacherforboss.domain.model.auth.AccountEntity
import com.company.teacherforboss.domain.model.auth.LogoutResponseEntity
import com.company.teacherforboss.domain.usecase.Member.AccountUsecase
import com.company.teacherforboss.domain.usecase.auth.LogoutUsecase
import com.company.teacherforboss.domain.usecase.auth.WithdrawUsecase
import com.company.teacherforboss.domain.model.auth.WithdrawResponseEntity
import com.company.teacherforboss.util.base.ConstsUtils.Companion.SIGNUP_SOCIAL_KAKAO
import com.company.teacherforboss.util.base.ConstsUtils.Companion.SIGNUP_SOCIAL_NAVER
import com.company.teacherforboss.util.base.LocalDataSource
import com.company.teacherforboss.util.base.LocalDataSource.Companion.FCM_TOKEN
import com.company.teacherforboss.util.view.UiState
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
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

    @Inject lateinit var localDataSource: LocalDataSource


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
        val fcmToken=localDataSource.getUserInfo(FCM_TOKEN)
        viewModelScope.launch {
            logoutUsecase(fcmToken).onSuccess { logoutResponseEntity->
                _logoutState.value=UiState.Success(logoutResponseEntity)

                val signupType = localDataSource.getSignupType()
                if(signupType== SIGNUP_SOCIAL_KAKAO) logoutKakao()
                else if(signupType== SIGNUP_SOCIAL_NAVER) logoutNaver()

                localDataSource.resetSignupType()
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

    fun logoutKakao(){
        // 로그아웃
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
            }
            else {
                Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
            }
        }
        // 연결 끊기
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Log.e(TAG, "연결 끊기 실패", error)
            }
            else {
                Log.i(TAG, "연결 끊기 성공. SDK에서 토큰 삭제 됨")
            }
        }
    }

    fun logoutNaver(){
        NaverIdLoginSDK.logout()
    }
    fun clearTokens() {
        tokenManager.clearAccessToken(context)
        tokenManager.clearRefreshToken(context)
    }
}