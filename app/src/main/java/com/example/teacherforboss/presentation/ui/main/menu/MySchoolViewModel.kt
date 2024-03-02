package com.example.teacherforboss.presentation.ui.main.menu

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.teacherforboss.data.model.response.members.ResponseGetProfileDto
import com.example.teacherforboss.domain.model.ProfileEntity
import com.example.teacherforboss.domain.usecase.ProfileUseCase
import com.example.teacherforboss.util.view.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MySchoolViewModel @Inject constructor(
     private val profileUseCase: ProfileUseCase
) :ViewModel(){
//    val _getProfileState=MutableStateFlow<UiState<ResponseGetProfileDto?>>(UiState.Empty)
//    val getProfileState get() = _getProfileState.asStateFlow()

    private val _profileLiveData = MutableLiveData<ProfileEntity>()
    val profileLiveData: LiveData<ProfileEntity> = _profileLiveData

    suspend fun getProfile(){
        viewModelScope.launch {
            try{
                val profileEntity=profileUseCase()
                _profileLiveData.value=profileEntity

            }catch (ex:Exception){
                Log.d("myschool",ex.message!!)
            }
        }

    }


}