package com.example.teacherforboss.presentation.ui.community.boss_talk.main

import androidx.lifecycle.ViewModel
import java.time.LocalDateTime
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.teacherforboss.domain.model.community.BossTalkPostsRequestEntity
import com.example.teacherforboss.domain.model.community.BossTalkPostsResponseEntity
import com.example.teacherforboss.domain.model.community.PostEntity
import com.example.teacherforboss.domain.usecase.BossTalkBookmarkUseCase
import com.example.teacherforboss.domain.usecase.BossTalkPostsUseCase
import com.example.teacherforboss.presentation.ui.community.boss_talk.main.card.BossTalkMainCard
import com.example.teacherforboss.presentation.ui.community.common.TalkMainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BossTalkMainViewModel @Inject constructor(
    private val bossTalkPostsUseCase: BossTalkPostsUseCase,
    private val bossTalkBookmarkUseCase: BossTalkBookmarkUseCase
) : ViewModel(),TalkMainViewModel {
    var _lastPostId=MutableLiveData<Long>(0L)
    val lastPostId:LiveData<Long>
        get() = _lastPostId

    var _size=MutableLiveData<Int>(10)
    val size:LiveData<Int>
        get() = _size
    var _sortBy=MutableLiveData<String>("latest")
    val sortBy:LiveData<String>
        get() = _sortBy
    var _keyword=MutableLiveData<String>("")
    val keyword:LiveData<String>
        get() = _keyword

    private val _getBossTalkPostLiveData=MutableLiveData<BossTalkPostsResponseEntity>()
    val getBossTalkPostLiveData:LiveData<BossTalkPostsResponseEntity>
        get() = _getBossTalkPostLiveData

    var _bossTalkPosts=MutableLiveData<List<PostEntity>>()
    val bossTalkPosts:LiveData<List<PostEntity>> =_bossTalkPosts

     val mockCardList = listOf<BossTalkMainCard>(
        BossTalkMainCard(
            title = "질문이 있습니다",
            content = "가나다라마박사 저는 누구누구인데요 이러이런 고민이 있습니당..",
            created_at = LocalDateTime.of(2024, 6, 12, 0, 0, 0),
            bookmark_count = "3",
            like_count = "2",
            comment_count = "4",
            liked = true,
            bookmarked = true,
            post_id = 12341234,
        ),
        BossTalkMainCard(
            title = "폐업 직전에 마지막 희망이라도..",
            content = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            created_at = LocalDateTime.of(2024, 5, 30, 0, 0, 0),
            bookmark_count = "2",
            like_count = "3",
            comment_count = "4",
            liked = false,
            bookmarked = true,
            post_id = 12341234,
        ),
        BossTalkMainCard(
            title = "어쩌구저쩌구 저는 할 말이 많습니다 질문 많아요",
            content = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            created_at = LocalDateTime.of(2024, 4, 2, 0, 0, 0),
            bookmark_count = "111",
            like_count = "43",
            comment_count = "12",
            liked = true,
            bookmarked = false,
            post_id = 12341234,
        ),
        BossTalkMainCard(
            title = "네번째 질문입니다 ㅋㅋ",
            content = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            created_at = LocalDateTime.of(2024, 12, 24, 0, 0, 0),
            bookmark_count = "111",
            like_count = "43",
            comment_count = "12",
            liked = false,
            bookmarked = false,
            post_id = 12341234,
        ),
        BossTalkMainCard(
            title = "다섯번째 질문입니다 ㅋㅋ",
            content = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            created_at = LocalDateTime.of(2024, 6, 12, 0, 0, 0),
            bookmark_count = "111",
            like_count = "43",
            comment_count = "12",
            liked = true,
            bookmarked = false,
            post_id = 12341234,
        ),
        BossTalkMainCard(
            title = "여섯번째 질문입니다 ㅋㅋ",
            content = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            created_at = LocalDateTime.of(2024, 6, 12, 0, 0, 0),
            bookmark_count = "111",
            like_count = "43",
            comment_count = "12",
            liked = true,
            bookmarked = true,
            post_id = 12341234,
        ),
        BossTalkMainCard(
            title = "일곱번째 질문입니다 ㅋㅋ",
            content = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            created_at = LocalDateTime.of(2024, 6, 12, 0, 0, 0),
            bookmark_count = "111",
            like_count = "43",
            comment_count = "12",
            liked = true,
            bookmarked = true,
            post_id = 12341234,
        ),
        BossTalkMainCard(
            title = "여덟번째 질문입니다 ㅋㅋ",
            content = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            created_at = LocalDateTime.of(2024, 6, 12, 0, 0, 0),
            bookmark_count = "111",
            like_count = "43",
            comment_count = "12",
            liked = false,
            bookmarked = true,
            post_id = 12341234,
        ),
        BossTalkMainCard(
            title = "아홉번째 질문입니다 ㅋㅋ",
            content = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            created_at = LocalDateTime.of(2024, 6, 12, 0, 0, 0),
            bookmark_count = "111",
            like_count = "43",
            comment_count = "12",
            liked = true,
            bookmarked = true,
            post_id = 12341234,
        ),
        BossTalkMainCard(
            title = "열번째 질문입니다 ㅋㅋ",
            content = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            created_at = LocalDateTime.of(2024, 6, 12, 0, 0, 0),
            bookmark_count = "111",
            like_count = "43",
            comment_count = "12",
            liked = true,
            bookmarked = true,
            post_id = 12341234,
        ),
        BossTalkMainCard(
            title = "열한번째 질문입니다 ㅋㅋ",
            content = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            created_at = LocalDateTime.of(2024, 6, 12, 0, 0, 0),
            bookmark_count = "111",
            like_count = "43",
            comment_count = "12",
            liked = true,
            bookmarked = true,
            post_id = 12341234,
        ),
        BossTalkMainCard(
            title = "열두번째 질문입니다 ㅋㅋ",
            content = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            created_at = LocalDateTime.of(2024, 6, 12, 0, 0, 0),
            bookmark_count = "111",
            like_count = "43",
            comment_count = "12",
            liked = true,
            bookmarked = true,
            post_id = 12341234,
        ),
        BossTalkMainCard(
            title = "열세번째 질문입니다 ㅋㅋ",
            content = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            created_at = LocalDateTime.of(2024, 6, 12, 0, 0, 0),
            bookmark_count = "111",
            like_count = "43",
            comment_count = "12",
            liked = true,
            bookmarked = true,
            post_id = 12341234,
        ),
        BossTalkMainCard(
            title = "열네번째 질문입니다 ㅋㅋ",
            content = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            created_at = LocalDateTime.of(2024, 6, 12, 0, 0, 0),
            bookmark_count = "111",
            like_count = "43",
            comment_count = "12",
            liked = true,
            bookmarked = true,
            post_id = 12341234,
        ),
        BossTalkMainCard(
            title = "열다섯번째 질문입니다 ㅋㅋ",
            content = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            created_at = LocalDateTime.of(2024, 6, 12, 0, 0, 0),
            bookmark_count = "111",
            like_count = "43",
            comment_count = "12",
            liked = true,
            bookmarked = true,
            post_id = 12341234,
        ),
        BossTalkMainCard(
            title = "열여섯번째 질문입니다 ㅋㅋ",
            content = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            created_at = LocalDateTime.of(2024, 6, 12, 0, 0, 0),
            bookmark_count = "111",
            like_count = "43",
            comment_count = "12",
            liked = true,
            bookmarked = true,
            post_id = 12341234,
        ),
        BossTalkMainCard(
            title = "열일곱번째 질문입니다 ㅋㅋ",
            content = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            created_at = LocalDateTime.of(2024, 6, 12, 0, 0, 0),
            bookmark_count = "111",
            like_count = "43",
            comment_count = "12",
            liked = true,
            bookmarked = true,
            post_id = 12341234,
        ),
        BossTalkMainCard(
            title = "열여덟번째 질문입니다 ㅋㅋ",
            content = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            created_at = LocalDateTime.of(2024, 6, 12, 0, 0, 0),
            bookmark_count = "111",
            like_count = "43",
            comment_count = "12",
            liked = true,
            bookmarked = true,
            post_id = 12341234,
        ),
        BossTalkMainCard(
            title = "열아홉번째 질문입니다 ㅋㅋ",
            content = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            created_at = LocalDateTime.of(2024, 6, 12, 0, 0, 0),
            bookmark_count = "111",
            like_count = "43",
            comment_count = "12",
            liked = true,
            bookmarked = true,
            post_id = 12341234,
        ),
        BossTalkMainCard(
            title = "스무번째 질문입니다 ㅋㅋ",
            content = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            created_at = LocalDateTime.of(2024, 6, 12, 0, 0, 0),
            bookmark_count = "111",
            like_count = "43",
            comment_count = "12",
            liked = true,
            bookmarked = true,
            post_id = 12341234,
        ),
        BossTalkMainCard(
            title = "스물한번째 질문입니다 ㅋㅋ",
            content = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            created_at = LocalDateTime.of(2024, 6, 12, 0, 0, 0),
            bookmark_count = "111",
            like_count = "43",
            comment_count = "12",
            liked = true,
            bookmarked = true,
            post_id = 12341234,
        ),
        BossTalkMainCard(
            title = "스물두번째 질문입니다 ㅋㅋ",
            content = "어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라 어쩌구저쩌구 샬라샬라",
            created_at = LocalDateTime.of(2024, 6, 12, 0, 0, 0),
            bookmark_count = "111",
            like_count = "43",
            comment_count = "12",
            liked = true,
            bookmarked = true,
            post_id = 12341234,
        ),
    )

    fun getBossTalkPosts(){
        viewModelScope.launch {
            try{
                val bossTalkPostsResponseEntity=bossTalkPostsUseCase(
                    BossTalkPostsRequestEntity(
                    lastPostId = lastPostId.value?:0L,
                    size=size.value?:10,
                    sortBy=sortBy.value?:"latest",
                    keyword =null
                ))
                _getBossTalkPostLiveData.value=bossTalkPostsResponseEntity

            }catch (ex:Exception){
            }
        }
    }
    suspend fun searchKeywordBossTalk(){
        viewModelScope.launch {
            try{
                val bossTalkPostsResponseEntity=bossTalkPostsUseCase(
                    BossTalkPostsRequestEntity(
                        lastPostId = lastPostId.value?:0L,
                        size=size.value?:0,
                        sortBy=null,
                        keyword =keyword.value
                    ))
                _getBossTalkPostLiveData.value=bossTalkPostsResponseEntity

            }catch (ex:Exception){
            }
        }
    }

    override fun setSortBy(sortBy: String) {
        Log.d("spinner","?2")
        var sort=""
        when(sortBy){
            "최신순"-> sort="latest"
            "조회수순"->sort="views"
            "좋아요순"->sort="likes"
        }
        _sortBy.value=sort
    }


}
