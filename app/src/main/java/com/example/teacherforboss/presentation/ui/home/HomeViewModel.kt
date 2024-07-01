package com.example.teacherforboss.presentation.ui.home

import androidx.lifecycle.ViewModel
import com.example.teacherforboss.R
import com.example.teacherforboss.presentation.model.BannerModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {
    private val _bannerItemList: MutableStateFlow<List<BannerModel>> = MutableStateFlow(emptyList())
    val bannerItemList get() = _bannerItemList

    private val _currentBannerPosition = MutableStateFlow(FIRST_BANNER_POSITION)
    val currentBannerPosition get() = _currentBannerPosition.asStateFlow()

    fun setBannerItems() {
        _bannerItemList.value = listOf(
            BannerModel(R.drawable.item_home_banner_guide),
            BannerModel(R.drawable.item_home_banner_event),
            BannerModel(R.drawable.item_home_banner_teacher),
        )
    }

    fun setCurrentBannerPosition(position: Int) {
        _currentBannerPosition.value = position
    }

    companion object {
        private const val FIRST_BANNER_POSITION = 0
    }
}
