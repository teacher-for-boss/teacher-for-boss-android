package com.example.teacherforboss.presentation.ui.home

import androidx.lifecycle.ViewModel
import com.example.teacherforboss.R
import com.example.teacherforboss.domain.model.home.BossTalkPopularPostEntity
import com.example.teacherforboss.domain.model.home.TeacherTalkPopularPostEntity
import com.example.teacherforboss.domain.model.home.WeeklyBestTeacherEntity
import com.example.teacherforboss.presentation.model.BannerModel
import com.example.teacherforboss.presentation.model.TeacherTalkShortCutModel
import com.example.teacherforboss.presentation.type.KeyWordType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {
    private val _bannerItemList: MutableStateFlow<List<BannerModel>> = MutableStateFlow(emptyList())
    val bannerItemList get() = _bannerItemList

    private val _currentBannerPosition = MutableStateFlow(FIRST_BANNER_POSITION)
    val currentBannerPosition get() = _currentBannerPosition.asStateFlow()

    private val _teacherTalkShortcutList: MutableStateFlow<List<TeacherTalkShortCutModel>> =
        MutableStateFlow(
            emptyList(),
        )
    val teacherTalkShortCutList get() = _teacherTalkShortcutList.asStateFlow()

    private val _teacherTalkPopularPostList: MutableStateFlow<List<TeacherTalkPopularPostEntity>> =
        MutableStateFlow(emptyList())
    val teacherTalkPopularPostList get() = _teacherTalkPopularPostList.asStateFlow()

    private val _bossTalkPopularPostList: MutableStateFlow<List<BossTalkPopularPostEntity>> =
        MutableStateFlow(emptyList())
    val bossTalkPopularPostList get() = _bossTalkPopularPostList.asStateFlow()

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

    fun setTeacherTalkPopularPost() {
        _teacherTalkPopularPostList.value = listOf(
            TeacherTalkPopularPostEntity(
                category = "운영",
                title = "고민이 있다네요",
                content = "이런 저런 고민이 있습니다. 혹시 저를 도와주실 분이 있나요?",
                comment = "16",
            ),
            TeacherTalkPopularPostEntity(
                category = "마케팅",
                title = "고민이 있다네요",
                content = "이런 저런 고민이 있습니다. 혹시 저를 도와주실 분이 있나요? 이런 저런 고민이 있습니다. 혹시 저를 도와주실 분이 있나요? 혹시 제발요!!",
                comment = "87",
            ),
            TeacherTalkPopularPostEntity(
                category = "직원관리",
                title = "최대한 길게 한번 타이틀 만들어볼게요 어떻게 되려나 함 봅시다 질문 늘리기 쭈우우욱 길어져라 길어져라",
                content = "이런 저런 고민이 있습니다. 혹시 저를 도와주실 분이 있나요?",
                comment = "0",
            ),
            TeacherTalkPopularPostEntity(
                category = "상권",
                title = "상권이 다 죽은 동네에서 장사하고 있습니다. 도와주세요. 이런 저런 고민이 있습니다. 혹시 저를 도와주실 분이 있나요?",
                content = "힝",
                comment = "3",
            ),
            TeacherTalkPopularPostEntity(
                category = "위생",
                title = "매일 물청소하는데 바퀴벌레가 나와요",
                content = "이런 저런 고민이 있습니다. 혹시 저를 도와주실 분이 있나요? ",
                comment = "03",
            ),
        )
    }

    fun setBossTalkPopularPost() {
        _bossTalkPopularPostList.value = listOf(
            BossTalkPopularPostEntity(
                number = "1",
                title = "제목 이러합니다. 어쩌구 저쩌구 이게 1등인 제목임용 얼마나 더 길어야 되지?",
            ),
            BossTalkPopularPostEntity(
                number = "2",
                title = "제목 이러합니다.",
            ),
            BossTalkPopularPostEntity(
                number = "3",
                title = "어쩌구 저쩌구",
            ),
            BossTalkPopularPostEntity(
                number = "4",
                title = "고민이 있다네요",
            ),
            BossTalkPopularPostEntity(
                number = "5",
                title = "우와앙! 우와앙!",
            ),
        )
    }

    fun setWeeklyBestTeacher() {
        _weeklyBestTeacherList.value = listOf(
            WeeklyBestTeacherEntity(
                profileImg = "https://img-cdn.theqoo.net/bJgQuT.jpg",
                nickName = "티쳐입니달라",
                specialty = "경영컨설턴트",
                career = "23",
                keyword = listOf(KeyWordType.PASSIONATE),
            ),
            WeeklyBestTeacherEntity(
                profileImg = "https://img-cdn.theqoo.net/bJgQuT.jpg",
                nickName = "티쳐입니달라",
                specialty = "경영컨설턴트",
                career = "23",
                keyword = listOf(KeyWordType.PASSIONATE, KeyWordType.CAREFUL, KeyWordType.PRACTICAL),
            ),
            WeeklyBestTeacherEntity(
                profileImg = "https://img-cdn.theqoo.net/bJgQuT.jpg",
                nickName = "티쳐입니달라",
                specialty = "경영컨설턴트",
                career = "23",
                keyword = listOf(
                    KeyWordType.PASSIONATE,
                    KeyWordType.CAREFUL,
                    KeyWordType.ANALYTICAL,
                    KeyWordType.DEPENDABLE,
                    KeyWordType.COMMUNICATE,
                ),
            ),
            WeeklyBestTeacherEntity(
                profileImg = "https://img-cdn.theqoo.net/bJgQuT.jpg",
                nickName = "티쳐입니달라",
                specialty = "경영컨설턴트",
                career = "23",
                keyword = listOf(KeyWordType.CAREFUL, KeyWordType.ACTIVE),
            ),
            WeeklyBestTeacherEntity(
                profileImg = "https://img-cdn.theqoo.net/bJgQuT.jpg",
                nickName = "티쳐입니달라",
                specialty = "경영컨설턴트",
                career = "23",
                keyword = listOf(KeyWordType.PASSIONATE, KeyWordType.CAREFUL),
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
