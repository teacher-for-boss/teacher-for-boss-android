package com.example.teacherforboss.presentation.ui.main.menu

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherforboss.domain.model.ProfileEntity
import com.example.teacherforboss.domain.usecase.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MySchoolViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase
) : ViewModel() {
    private val _profileLiveData = MutableLiveData<ProfileEntity>()
    val profileLiveData: LiveData<ProfileEntity> = _profileLiveData

    suspend fun getProfile() {
        viewModelScope.launch {
            try {
                val profileEntity = profileUseCase()
                _profileLiveData.value = profileEntity

            } catch (ex: Exception) {
            }
        }

    }


}