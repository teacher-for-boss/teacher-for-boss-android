package com.example.teacherforboss.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teacherforboss.R
import com.example.teacherforboss.domain.model.home.BossTalkPopularPostEntity
import com.example.teacherforboss.domain.model.home.TeacherTalkPopularPostEntity
import com.example.teacherforboss.domain.model.home.WeeklyBestTeacherEntity
import com.example.teacherforboss.domain.usecase.home.GetBossTalkPopularPostUseCase
import com.example.teacherforboss.domain.usecase.home.GetTeacherTalkPopularPostUseCase
import com.example.teacherforboss.presentation.model.BannerModel
import com.example.teacherforboss.presentation.model.TeacherTalkShortCutModel
import com.example.teacherforboss.presentation.type.KeywordType
import com.example.teacherforboss.util.view.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTeacherTalkPopularPostUseCase: GetTeacherTalkPopularPostUseCase,
    private val getBossTalkPopularPostUseCase: GetBossTalkPopularPostUseCase,
) : ViewModel() {
    private val _bannerItemList: MutableStateFlow<List<BannerModel>> = MutableStateFlow(emptyList())
    val bannerItemList get() = _bannerItemList

    private val _currentBannerPosition = MutableStateFlow(FIRST_BANNER_POSITION)
    val currentBannerPosition get() = _currentBannerPosition.asStateFlow()

    private val _teacherTalkShortcutList: MutableStateFlow<List<TeacherTalkShortCutModel>> =
        MutableStateFlow(
            emptyList(),
        )
    val teacherTalkShortCutList get() = _teacherTalkShortcutList.asStateFlow()

    private val _teacherTalkPopularPostListState: MutableStateFlow<UiState<List<TeacherTalkPopularPostEntity>>> =
        MutableStateFlow(UiState.Empty)
    val teacherTalkPopularPostListState get() = _teacherTalkPopularPostListState.asStateFlow()

    private val _bossTalkPopularPostListState: MutableStateFlow<UiState<List<BossTalkPopularPostEntity>>> =
        MutableStateFlow(UiState.Empty)
    val bossTalkPopularPostListState get() = _bossTalkPopularPostListState.asStateFlow()

    private val _weeklyBestTeacherList: MutableStateFlow<List<WeeklyBestTeacherEntity>> =
        MutableStateFlow(emptyList())
    val weeklyBestTeacherList get() = _weeklyBestTeacherList.asStateFlow()

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

    fun setTeacherTalkShortcutItems() {
        _teacherTalkShortcutList.value = listOf(
            TeacherTalkShortCutModel(R.drawable.ic_category_all_44, R.string.home_teacher_talk_all),
            TeacherTalkShortCutModel(
                R.drawable.ic_category_marketing_44,
                R.string.home_teacher_talk_marketing,
            ),
            TeacherTalkShortCutModel(
                R.drawable.ic_category_hygiene_44,
                R.string.home_teacher_talk_hygiene,
            ),
            TeacherTalkShortCutModel(
                R.drawable.ic_category_area_44,
                R.string.home_teacher_talk_area,
            ),
            TeacherTalkShortCutModel(
                R.drawable.ic_category_operate_44,
                R.string.home_teacher_talk_operate,
            ),
            TeacherTalkShortCutModel(
                R.drawable.ic_category_employee_44,
                R.string.home_teacher_talk_employee,
            ),
            TeacherTalkShortCutModel(
                R.drawable.ic_category_interior_44,
                R.string.home_teacher_talk_interior,
            ),
            TeacherTalkShortCutModel(
                R.drawable.ic_category_policy_44,
                R.string.home_teacher_talk_policy,
            ),
        )
    }

    fun getTeacherTalkPopularPost() {
        viewModelScope.launch {
            getTeacherTalkPopularPostUseCase().onSuccess { teacherTalkPopularPostEntity ->
                _teacherTalkPopularPostListState.value =
                    UiState.Success(teacherTalkPopularPostEntity)
            }.onFailure { exception: Throwable ->
                _teacherTalkPopularPostListState.value = UiState.Error(exception.message)
            }
        }
    }

    fun getBossTalkPopularPost() {
        viewModelScope.launch {
            getBossTalkPopularPostUseCase().onSuccess { bossTalkPopularPostEntity ->
                _bossTalkPopularPostListState.value = UiState.Success(bossTalkPopularPostEntity)
            }.onFailure { exception: Throwable ->
                _bossTalkPopularPostListState.value = UiState.Error(exception.message)
            }
        }
    }

    fun setWeeklyBestTeacher() {
        _weeklyBestTeacherList.value = listOf(
            WeeklyBestTeacherEntity(
                profileImg = "https://img-cdn.theqoo.net/bJgQuT.jpg",
                nickName = "티쳐입니달라",
                specialty = "경영컨설턴트",
                career = "23",
                keyword = listOf(KeywordType.PASSIONATE),
            ),
            WeeklyBestTeacherEntity(
                profileImg = "https://img-cdn.theqoo.net/bJgQuT.jpg",
                nickName = "티쳐입니달라",
                specialty = "경영컨설턴트",
                career = "23",
                keyword = listOf(
                    KeywordType.PASSIONATE,
                    KeywordType.CAREFUL,
                    KeywordType.PRACTICAL,
                ),
            ),
            WeeklyBestTeacherEntity(
                profileImg = "https://img-cdn.theqoo.net/bJgQuT.jpg",
                nickName = "티쳐입니달라",
                specialty = "경영컨설턴트",
                career = "23",
                keyword = listOf(
                    KeywordType.PASSIONATE,
                    KeywordType.CAREFUL,
                    KeywordType.ANALYTICAL,
                    KeywordType.DEPENDABLE,
                    KeywordType.COMMUNICATE,
                ),
            ),
            WeeklyBestTeacherEntity(
                profileImg = "https://img-cdn.theqoo.net/bJgQuT.jpg",
                nickName = "티쳐입니달라",
                specialty = "경영컨설턴트",
                career = "23",
                keyword = listOf(KeywordType.CAREFUL, KeywordType.ACTIVE),
            ),
            WeeklyBestTeacherEntity(
                profileImg = "https://img-cdn.theqoo.net/bJgQuT.jpg",
                nickName = "티쳐입니달라",
                specialty = "경영컨설턴트",
                career = "23",
                keyword = listOf(KeywordType.PASSIONATE, KeywordType.CAREFUL),
            ),
        )
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
