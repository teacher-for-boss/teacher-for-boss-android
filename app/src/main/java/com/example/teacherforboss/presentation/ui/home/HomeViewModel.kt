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
            BannerModel(R.drawable.item_home_banner_guide, BANNER_GUIDE_LINK),
            BannerModel(R.drawable.item_home_banner_event, BANNER_EVENT_LINK),
            BannerModel(R.drawable.item_home_banner_teacher, BANNER_TEACHER_LINK),
        )
    }

    fun setCurrentBannerPosition(position: Int) {
        _currentBannerPosition.value = position
    }

    companion object {
        private const val FIRST_BANNER_POSITION = 0
        private const val BANNER_GUIDE_LINK =
            "https://beautiful-pharaoh-385.notion.site/2a26c9826eef47adbc6852a8ad400691?pvs=4"
        private const val BANNER_EVENT_LINK =
            "https://beautiful-pharaoh-385.notion.site/55ccfe37dcf946d08430f3c5e2fd72c8?pvs=4"
        private const val BANNER_TEACHER_LINK =
            "https://beautiful-pharaoh-385.notion.site/cfd1ba1e75c6488ba4f103dce948b71c?pvs=4"
    }
}
