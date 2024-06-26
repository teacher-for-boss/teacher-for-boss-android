package com.example.teacherforboss.presentation.ui.home

import androidx.lifecycle.ViewModel
import com.example.teacherforboss.R
import com.example.teacherforboss.presentation.model.BannerModel
import kotlinx.coroutines.flow.MutableStateFlow

class HomeViewModel : ViewModel() {
    private val _bannerItemList: MutableStateFlow<List<BannerModel>> = MutableStateFlow(emptyList())
    val bannerItemList get() = _bannerItemList

    fun setBannerItems() {
        _bannerItemList.value = listOf(
            BannerModel(R.drawable.item_home_banner_guide),
            BannerModel(R.drawable.item_home_banner_event),
            BannerModel(R.drawable.item_home_banner_teacher),
        )
    }
}
